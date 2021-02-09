package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public String addCredential(Authentication authentication,
                                @ModelAttribute Credential credential,
                                RedirectAttributes redirectAttributes){
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);

        if(credential.getCredentialId() == null) {
            credentialService.addCredential(credential);
        } else {
            credentialService.updateCredential(credential);
        }

        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        redirectAttributes.addFlashAttribute("isSuccess", true);
        return "redirect:/result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(RedirectAttributes redirectAttributes,
                                   @ModelAttribute Credential credential,
                                   @PathVariable Integer credentialId) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        redirectAttributes.addFlashAttribute("isSuccess", true);
        return "redirect:/result";
    }
}
