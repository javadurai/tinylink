package com.github.jabadurai.tinylink.service;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.entities.UserUrlOwnership;
import com.github.jabadurai.tinylink.repositories.UrlRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UrlService  extends BaseService {

    List<String> updatableFields = List.of("fullName", "username", "email", "role");

    private final static Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final UrlRepository urlRepository;

    private final UserService userService;

    public UrlService(UrlRepository urlRepository, UserService userService){
        this.urlRepository = urlRepository;
        this.userService = userService;
    }

    public Iterable<Url> getLinks(boolean allLinks){
        List<Url> all = urlRepository.findAll();

        Iterator<Url> urlIterator = all.iterator();

        Integer currentUserId = userService.currentLoggedInUserId().get();

        if(userService.currentLoggedInUserId().isPresent()){
            if(!allLinks) {

                while (urlIterator.hasNext()) {

                    Url link = urlIterator.next();

                    logger.info("Iterating Url - " + link.getShortUrl());

                    List<UserUrlOwnership> owners = link.getUserUrlOwnerships();

                    boolean isLinkOwnedByCurrentUser = owners.stream().map(o -> o.getUser().getId()).anyMatch(id -> id.equals(currentUserId));

                    if (!isLinkOwnedByCurrentUser) {
                        urlIterator.remove();
                    }

                }
            }
        }

        return all;
    }

    public Page<Url> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.urlRepository.findAll(pageable);
    }

    public Page<Url> findOwnedByMePaginated(int pageNo, int pageSize) {
        if(userService.currentLoggedInUserId().isPresent()){
            Integer currentUserId = userService.currentLoggedInUserId().get();
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
            return this.urlRepository.findByOwnedByUser(currentUserId, pageable);
        } else{
            return findPaginated(pageNo, pageSize);
        }
    }

    public ResponseEntity<Object> saveLink(Url urlDto, BindingResult bindingResult) {

        try {

            if(urlDto.getId() != null){
                Optional<Url> urlFromDb = urlRepository.findById(urlDto.getId());
                if(urlFromDb.isPresent()){
                    Url updatedUrl = urlFromDb.get();
                    updatedUrl.setShortUrl(urlDto.getShortUrl());
                    updatedUrl.setOriginalUrl(urlDto.getOriginalUrl());

                    urlDto = updatedUrl;
                }
            }

            ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "shortUrl", "NotEmpty.urlForm.shortUrl",null, "Short Url is mandatory");
            ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "originalUrl", "NotEmpty.urlForm.originalUrl",null, "Original Url is mandatory");

            if(bindingResult.hasErrors()){
                return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
            } else {
                urlRepository.save(urlDto);
                // Return a successful response
                Map<String, String> response = new HashMap<>();
                response.put("message", "Link saved successfully!");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (DataIntegrityViolationException ex) {
            // This exception is thrown when unique constraint is violated
            bindingResult.addError(new ObjectError("shortUrl","Short link with same name already exists, Please choose a different one"));
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            // Handle other exceptions
            bindingResult.addError(new ObjectError("shortUrl","Unable to process your request"));
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteLink(Integer id) {
        try {
            urlRepository.deleteById(id);

            // Return a successful response
            Map<String, String> response = new HashMap<>();
            response.put("message", "URL removed successfully!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
