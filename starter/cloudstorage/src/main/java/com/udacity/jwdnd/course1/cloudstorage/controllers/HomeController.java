package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private UserService userService;
    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionService() {
        return new EncryptionService();
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        System.out.println("SESSION NAME: " + userName);
        User user;
        if(userName != null){
            user = userService.getUser(userName);
        } else{
            user = userService.getUser(authentication.getName());
        }
        Integer userId = user.getUserId();
        System.out.println("ID: " + userId);
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("files", fileService.getFilesByUserId(userId));
        model.addAttribute("credentials", credentialService.getAllByUserId(userId));
        model.addAttribute("userName", user.getUserName());
        return "home";
    }

    @GetMapping("/result")
    public String getResult(){
        return "result";
    }

}