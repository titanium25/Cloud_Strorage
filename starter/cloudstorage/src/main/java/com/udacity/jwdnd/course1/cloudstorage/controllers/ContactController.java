package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("contact")
public class ContactController {

    private UserService userService;
    private HashService hashService;

    public ContactController(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @ModelAttribute("user")
    public User getUser(Authentication authentication, HttpServletRequest request){
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        if(userName != null)
        {
            return userService.getUser(userName);
        } else{
            return userService.getUser(authentication.getName());
        }
    }

    @GetMapping
    public String getProfile() {
        return "contact";
    }


}


