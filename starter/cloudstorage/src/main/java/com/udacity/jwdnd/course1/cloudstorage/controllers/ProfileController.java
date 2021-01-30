package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("profile")
public class ProfileController {

    private UserService userService;
    private HashService hashService;

    public ProfileController(UserService userService, HashService hashService) {
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
        return "profile";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute User user, Model model, Authentication authentication, HttpServletRequest request){
        User authUser;
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        if(userName != null){
            authUser = userService.getUser(userName);
        } else{
            authUser = userService.getUser(authentication.getName());
        }
        user.setUserId(authUser.getUserId());
        user = isSamePassword(authUser, user);
        userService.updateUser(user);
        session.setAttribute("userName", user.getUserName());
        model.addAttribute("profileSuccess", true);
        model.addAttribute("profileMessage", "Your profile was edited successfully");
        return "profile";
    }
    public User isSamePassword(User databaseUser, User formUser){
        String hashedPassword;
        if (!databaseUser.getPassword().equals(formUser.getPassword())) {
            hashedPassword = hashService.getHashedValue(formUser.getPassword(), databaseUser.getSalt());
            formUser.setPassword(hashedPassword);
        }
        return formUser;
    }
}


