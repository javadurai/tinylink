package com.github.jabadurai.tinylink.controllers;

import com.github.jabadurai.tinylink.entities.User;
import com.github.jabadurai.tinylink.service.UserService;
import com.github.jabadurai.tinylink.utils.Paginator;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends Paginator<User> {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public String listUsers(Model model, @RequestParam(defaultValue = "1") int pageNo){

        Page<User> page = userService.findPaginated(pageNo, PAGE_SIZE);
        List<User> listUsers = page.getContent();

        addPageDetailsToModel(model, pageNo, page, "users");

        model.addAttribute("listUsers", listUsers);

        return "users";
    }
}
