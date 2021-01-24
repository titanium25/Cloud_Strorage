package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.io.IOException;
import java.security.Principal;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {


    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) throws IOException {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        boolean fileExists = fileService.fileExists(fileUpload.getOriginalFilename(), userId);
        redirectAttributes.addFlashAttribute("activeTab", "files");

        if(fileUpload.isEmpty()){
            redirectAttributes.addFlashAttribute("isSuccess", false);
            redirectAttributes.addFlashAttribute("errorText", "You must choose a file to upload.");
        } else if (fileExists) {
            redirectAttributes.addFlashAttribute("isSuccess", false);
            redirectAttributes.addFlashAttribute("errorText", "A file with name " + fileUpload.getOriginalFilename() + " already exists. Please rename or choose a different file to upload.");
        } else {
            fileService.addFile(fileUpload, userId);
            redirectAttributes.addFlashAttribute("isSuccess", true);
            redirectAttributes.addFlashAttribute("errorText", "File with name " + fileUpload.getOriginalFilename() + " was successfully uploaded");
        }

        return "redirect:/result";
    }

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity downloadFile(@PathVariable Integer fileId) {
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId,
                             RedirectAttributes redirectAttributes) {
        fileService.deleteByFileId(fileId);
        redirectAttributes.addFlashAttribute("isSuccess", true);
        redirectAttributes.addFlashAttribute("errorText", "File " + fileService.getFileById(fileId)  + " was successfully deleted");
        return "redirect:/result";
    }
}