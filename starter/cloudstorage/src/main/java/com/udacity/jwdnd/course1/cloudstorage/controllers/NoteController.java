package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addNote(Authentication authentication,
                           @ModelAttribute Note note,
                           RedirectAttributes redirectAttributes) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);

        if (note.getNoteId() == null) {
            noteService.addNote(note);
        } else {
            noteService.updateNote(note);
        }

        redirectAttributes.addFlashAttribute("activeTab", "notes");
        redirectAttributes.addFlashAttribute("isSuccess", true);
        return "redirect:/result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(RedirectAttributes redirectAttributes,
                             @ModelAttribute Note note,
                             @PathVariable Integer noteId) {
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("activeTab", "notes");
        redirectAttributes.addFlashAttribute("isSuccess", true);
        return "redirect:/result";
    }

}
