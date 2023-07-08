package com.github.jabadurai.go.urlshortner.controllers;

import com.github.jabadurai.go.urlshortner.entities.Url;
import com.github.jabadurai.go.urlshortner.repositories.UrlRepository;
import com.github.jabadurai.go.urlshortner.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

@Controller
public class UrlController {

    private final static Logger logger = LoggerFactory.getLogger(UrlController.class);

    @Autowired
    private UrlRepository urlRepository;

    @RequestMapping("/")
    public String listAll(Url search, Model model){
        Iterable<Url> all;
        if(StringUtils.isNotEmpty(search.getOriginalUrl()) && StringUtils.isNotEmpty(search.getShortUrl()))
            all = urlRepository.findByOriginalUrlAndShortUrl(search.getOriginalUrl(), search.getShortUrl());
        else if(StringUtils.isNotEmpty(search.getOriginalUrl()))
            all = urlRepository.findByOriginalUrl(search.getOriginalUrl());
        else if(StringUtils.isNotEmpty(search.getShortUrl()))
            all = urlRepository.findByShortUrl(search.getShortUrl());
        else
            all = urlRepository.findAll();


        model.addAttribute("list", all);
        logger.info(all.toString());
        System.out.println(all.toString());
        model.addAttribute("search", search != null ? search : new Url());
        return "index";
    }

    @GetMapping("/my-urls")
    public String myUrls(){
        return "index";
    }

    @GetMapping("/all-urls")
    public String allUrls(){
        return "index";
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
            Integer clickCount = currentUrl.getClickCount()  != null ? currentUrl.getClickCount() : 0;
            currentUrl.setClickCount(clickCount +1);
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

    @GetMapping("/manage-url/remove/{id}")
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
