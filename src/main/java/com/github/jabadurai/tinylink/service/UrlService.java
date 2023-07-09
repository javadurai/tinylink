package com.github.jabadurai.tinylink.service;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.entities.UserUrlOwnership;
import com.github.jabadurai.tinylink.repositories.UrlRepository;
import com.github.jabadurai.tinylink.repositories.UserUrlOwnershipRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UrlService {

    private final static Logger logger = LoggerFactory.getLogger(UrlService.class);

    private UrlRepository urlRepository;

    private UserDetailsServiceImpl userDetailsServiceImpl;

    public UrlService(UrlRepository urlRepository, UserDetailsServiceImpl userDetailsServiceImpl){
        this.urlRepository = urlRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public Iterable<Url> getLinks(boolean allLinks){
        List<Url> all = urlRepository.findAll();

        Iterator<Url> urlIterator = all.iterator();

        Integer currentUserId = userDetailsServiceImpl.currentLoggedInUserId().get();

        if(userDetailsServiceImpl.currentLoggedInUserId().isPresent()){
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
}
