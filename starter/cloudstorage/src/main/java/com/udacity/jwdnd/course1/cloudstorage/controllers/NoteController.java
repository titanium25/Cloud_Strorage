package com.udacity.jwdnd.course1.cloudstorage.controllers;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/notes")
    public String postNote(Authentication authentication,
                           @ModelAttribute Note note,
                           RedirectAttributes redirectAttributes) {


        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

            note.setUserid(userId);
            noteService.createNote(note);
            redirectAttributes.addFlashAttribute("activeTab", "notes");
            redirectAttributes.addFlashAttribute("isSuccess", true);

        return "redirect:/result";

//        User user = userService.getUser(authentication.getName());
//        Integer userid = user.getUserId();
//        note.setUserid(userid);
//        noteService.createNote(note);
//        model.addAttribute("notes", noteService.getNotesByUserId(userid));
//        return "home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(Authentication authentication,
                             RedirectAttributes redirectAttributes,
                             @ModelAttribute Note note,
                             @PathVariable Integer noteId) {
        User user = userService.getUser(authentication.getName());
        Integer userid = user.getUserId();
        note.setUserid(userid);
        noteService.deleteNote(note, noteId);
        redirectAttributes.addFlashAttribute("activeTab", "notes");
        redirectAttributes.addFlashAttribute("isSuccess", true);

        return "redirect:/result";
    }

}
