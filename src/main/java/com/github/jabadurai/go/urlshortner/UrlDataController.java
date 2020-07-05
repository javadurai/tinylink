package com.github.jabadurai.go.urlshortner;

import com.github.jabadurai.go.urlshortner.entities.UrlData;
import com.github.jabadurai.go.urlshortner.repositories.UrlDataRepository;
import com.github.jabadurai.go.urlshortner.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@Controller
public class UrlDataController {

    @Autowired
    private UrlDataRepository urlDataRepository;

    @RequestMapping("/")
    public String listAll(UrlData search, Model model){
        Iterable<UrlData> all;
        if(StringUtils.isNotEmpty(search.getFullUrl()) && StringUtils.isNotEmpty(search.getShortUrl()))
            all = urlDataRepository.findByFullUrlAndShortUrl(search.getFullUrl(), search.getShortUrl());
        else if(StringUtils.isNotEmpty(search.getFullUrl()))
            all = urlDataRepository.findByFullUrl(search.getFullUrl());
        else if(StringUtils.isNotEmpty(search.getShortUrl()))
            all = urlDataRepository.findByShortUrl(search.getShortUrl());
        else
            all = urlDataRepository.findAll();


        model.addAttribute("list", all);
        model.addAttribute("search", search != null ? search : new UrlData());
        return "index";
    }

    @GetMapping({"/manage-url", "/manage-url/{id}"})
    public String addNew(@PathVariable(required = false) Long id, Model model){
        UrlData urlData = null;
        if(id != null ){
            Optional<UrlData> urlDataFetch = urlDataRepository.findById(id);
            if(urlDataFetch.isPresent()){
                urlData = urlDataFetch.get();
            }
        } else {
            urlData = new UrlData();
        }
        model.addAttribute("urlData", urlData);
        return "manage-url";
    }

    @GetMapping("/go/{shortUrl}")
    public String go(@PathVariable String shortUrl, Model model, HttpServletResponse httpServletResponse){
        List<UrlData> urlData = urlDataRepository.findByShortUrl(shortUrl);
        if(urlData != null && urlData.size() != 0){
            httpServletResponse.setHeader("Location", urlData.get(0).getFullUrl());
            httpServletResponse.setStatus(302);
            return null;
        }
        model.addAttribute("list", urlDataRepository.findAll());
        model.addAttribute("search", new UrlData());
        return "index";
    }

    private List<String> validateData(UrlData urlData){
        List<String> errors = new ArrayList<String>();
        if(StringUtils.isEmpty(urlData.getShortUrl())){
            errors.add("Please enter Short URL");
        } else if(urlData.getShortUrl().length() > 50 || urlData.getShortUrl().length() < 4){
            errors.add("Short URL must be between 4 and 50 characters");
        }
        if(StringUtils.isEmpty(urlData.getFullUrl())){
            errors.add("Please enter Full URL");
        }

        return errors;
    }

    @PostMapping("/manage-url")
    public String save(@Valid UrlData urlData, BindingResult validator, Model model){
        if(validator.hasErrors()){
            model.addAttribute("urlData", urlData);
            return "manage-url";
        } else {
            urlDataRepository.save(urlData);
            return "redirect:/";
        }
    }

    @GetMapping("/manage-url/remove/{id}")
    public String removeUrl(@PathVariable Long id){
        urlDataRepository.deleteById(id);
        return "redirect:/";
    }
}
