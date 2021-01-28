package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
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

            credentialService.updateCredential(credential);
        }


        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        redirectAttributes.addFlashAttribute("isSuccess", true);

        return "redirect:/result";
    }

//    @GetMapping(value = "/getDecryptedCredential", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public Map<String, String> getDecryptedCredential(@RequestParam Integer credentialId){
//
//        Credential credential = credentialService.getByCredentialId(credentialId);
//        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
//        Map<String, String> map = new HashMap();
//        map.put("encryptedPassword", credential.getPassword());
//        map.put("decryptedPassword", decryptedPassword);
//        return map;
//
//    }

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
