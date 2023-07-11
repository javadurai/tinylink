package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.repositories.UrlRepository;
import com.github.jabadurai.tinylink.service.UrlService;
import com.github.jabadurai.tinylink.utils.Paginator;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UrlController extends Paginator<Url> {

    private final static Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final UrlRepository urlRepository;

    private final UrlService urlService;

    UrlController(UrlRepository urlRepository, UrlService urlService){
        this.urlRepository = urlRepository;
        this.urlService = urlService;
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
        return "index";
    }

}
