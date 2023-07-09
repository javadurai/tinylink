package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.repositories.UrlRepository;
import com.github.jabadurai.tinylink.service.UrlService;
import com.github.jabadurai.tinylink.utils.Paginator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

@Controller
public class UrlController extends Paginator<Url> {

    private final static Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final UrlRepository urlRepository;

    private final UrlService urlService;

    UrlController(UrlRepository urlRepository, UrlService urlService){
        this.urlRepository = urlRepository;
        this.urlService = urlService;
    }

    @RequestMapping("/")
    @Transactional
    public String dashboard(Model model){

        return "dashboard";
    }

    @GetMapping("/my-links")
    public String myUrls(Model model, @RequestParam(defaultValue = "1") int pageNo){
        Page<Url> page = urlService.findOwnedByMePaginated(pageNo, PAGE_SIZE);
        List<Url> listUsers = page.getContent();

        addPageDetailsToModel(model, pageNo, page, "my-links");

        model.addAttribute("listLinks", listUsers);
        model.addAttribute("title", "Links Owned by Me");

        return "links";
    }

    @GetMapping("/all-links")
    public String allUrls(Model model, @RequestParam(defaultValue = "1") int pageNo){
        Page<Url> page = urlService.findPaginated(pageNo, PAGE_SIZE);
        List<Url> listUsers = page.getContent();

        addPageDetailsToModel(model, pageNo, page, "all-links");

        model.addAttribute("listLinks", listUsers);
        model.addAttribute("title", "All Links in System");
        return "links";
    }

    @GetMapping({"/manage-url", "/manage-url/{id}"})
    public String manageUrl(@PathVariable(required = false) Integer id, Model model){
        Url urlData = null;
        if(id != null ){
            Optional<Url> urlDataFetch = urlRepository.findById(id);
            if(urlDataFetch.isPresent()){
                urlData = urlDataFetch.get();
            }
        } else {
            urlData = new Url();
        }
        model.addAttribute("urlData", urlData);
        return "manage-url";
    }

    @GetMapping("/go/{shortUrl}")
    public String go(@PathVariable String shortUrl, Model model, HttpServletResponse httpServletResponse){
        List<Url> urlData = urlRepository.findByShortUrl(shortUrl);
        if(urlData != null && urlData.size() != 0){
            // Update clicks
            Url currentUrl = urlData.get(0);
            int clickCount = currentUrl.getClickCount()  != null ? currentUrl.getClickCount() : 0;
            currentUrl.setClickCount(clickCount + 1);
            urlRepository.save(currentUrl);

            httpServletResponse.setHeader("Location", currentUrl.getOriginalUrl());
            httpServletResponse.setStatus(302);
            return null;
        }
        model.addAttribute("list", urlRepository.findAll());
        model.addAttribute("search", new Url());
        return "index";
    }

    @PostMapping("/manage-url")
    public String save(@Valid Url urlData, BindingResult validator, Model model, RedirectAttributes redirAttrs){
        if(validator.hasErrors()){
            model.addAttribute("urlData", urlData);
            redirAttrs.addFlashAttribute("error", "Sorry. Please fix the below errors !");
            return "manage-url";
        } else {
            // validate unique short_url
            if(urlData.getId() == null){
                List<Url> getData = urlRepository.findByShortUrl(urlData.getShortUrl());
                if(getData != null && getData.size() > 0){
                    validator.addError(new FieldError(Url.class.getName(), "shortUrl", "Short URL '"+ urlData.getShortUrl() + "' already exist in system. please choose a different text"));
                    model.addAttribute("urlData", urlData);
                    redirAttrs.addFlashAttribute("error", "Sorry. Please fix the below errors !");
                    return "manage-url";
                }
            }

            urlRepository.save(urlData);
            redirAttrs.addFlashAttribute("success", "Everything went just fine with save!");
            return "redirect:/";
        }
    }

    @DeleteMapping("/manage-url/{id}")
    public String removeUrl(@PathVariable Integer id, RedirectAttributes redirAttrs){
        urlRepository.deleteById(id);
        redirAttrs.addFlashAttribute("success", "Removed short url !");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
