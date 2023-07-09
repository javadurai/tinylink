package com.github.jabadurai.tinylink.utils;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public abstract class Paginator<T> {

    public static int PAGE_SIZE = 15;

    public void addPageDetailsToModel(Model model, int pageNo, Page<T> page, String baseUrl) {
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        int startPage = Math.max(1, pageNo - 3);
        int endPage = Math.min(startPage + 6, page.getTotalPages());
        if (endPage == page.getTotalPages()) {
            startPage = Math.max(1, page.getTotalPages() - 6);
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("baseURL", "/" + baseUrl);
    }
}
