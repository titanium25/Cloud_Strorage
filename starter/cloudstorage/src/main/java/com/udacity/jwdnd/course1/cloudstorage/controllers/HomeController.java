package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private NoteService noteService;
    private UserService userService;
    private FileService fileService;

    public HomeController(NoteService noteService, UserService userService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("files", fileService.getFilesByUserId(userId));
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