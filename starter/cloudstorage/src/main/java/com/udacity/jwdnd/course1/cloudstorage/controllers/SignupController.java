package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user,
                             @RequestParam("password") String pass1,
                             @RequestParam("password2") String pass2,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        String signupError = null;

        if(!pass1.equals(pass2)) {
            signupError = "The password are different!";
        }

        if (!userService.isUsernameAvailable(user.getUserName())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("SuccessMessage","Sign Up Successfully");
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}