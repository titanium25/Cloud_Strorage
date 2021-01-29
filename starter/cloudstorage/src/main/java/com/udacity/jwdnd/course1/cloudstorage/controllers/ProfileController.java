package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("profile")
public class ProfileController {

    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User getUser(Authentication authentication){
        return userService.getUser(authentication.getName());
    }

    @GetMapping
    public String getProfile() {
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute User user, Model model){
        userService.updateUser(user);
        model.addAttribute("profileSuccess", true);
        model.addAttribute("profileMessage", "Your profile was edited successfully");
        return "profile";
    }
}
