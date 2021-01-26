package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String addNote(Authentication authentication,
                           @ModelAttribute Note note,
                           RedirectAttributes redirectAttributes) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);

        if (note.getNoteId() == null) {            //add note
            noteService.addNote(note);
        } else {                                   //update note
            int noteId = note.getNoteId();
            String noteTitle = note.getNoteTitle();
            String noteDescription = note.getNoteDescription();
            noteService.updateNote(noteId, noteTitle, noteDescription);
        }

            redirectAttributes.addFlashAttribute("activeTab", "notes");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        return "redirect:/result";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(RedirectAttributes redirectAttributes,
                             @ModelAttribute Note note,
                             @PathVariable Integer noteId) {
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("activeTab", "notes");
        redirectAttributes.addFlashAttribute("isSuccess", true);
        return "redirect:/result";
    }

}
