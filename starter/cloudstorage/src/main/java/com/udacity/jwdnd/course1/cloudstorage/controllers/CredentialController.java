package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credential")
    public String addCredential(Authentication authentication,
                                @ModelAttribute Credential credential,
                                RedirectAttributes redirectAttributes){
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);

        if(credential.getCredentialId() == null) {
            credentialService.addCredential(credential);
        } else {
            int credentialId = credential.getCredentialId();
            String userName = credential.getUserName();
            String url = credential.getUrl();
            String key = credential.getKey();
            String password = credential.getPassword();
            credentialService.updateCredential(credentialId,userName, url, key, password);
        }


        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        redirectAttributes.addFlashAttribute("isSuccess", true);

        return "redirect:/result";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(RedirectAttributes redirectAttributes,
                                   @ModelAttribute Credential credential,
                                   @PathVariable Integer credentialId) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        redirectAttributes.addFlashAttribute("isSuccess", true);
        return "redirect:/result";
    }
}
