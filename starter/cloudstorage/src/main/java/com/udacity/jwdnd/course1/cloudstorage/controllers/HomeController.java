package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    private NoteService noteService;
    private UserService userService;
    private FileService fileService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionService() {
        return new EncryptionService();
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("files", fileService.getFilesByUserId(userId));
        model.addAttribute("credentials", credentialService.getAllByUserId(userId));
        model.addAttribute("activeTab", "files");
        return "home";
    }

    @GetMapping("/result")
    public String getResult(){
        return "result";
    }


    @GetMapping("/logout")
    public String logoutView() {
        return "redirect:/login?logout";
    }


}