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

    //https://github.com/addejans/cloudstorage/blob/master/src/main/java/com/udacity/jwdnd/course1/cloudstorage/controller/FileController.java
    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes,
                             Model model) throws IOException {



        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        boolean fileExists = fileService.fileExists(fileUpload.getOriginalFilename(), userId);

        if (fileExists){
            redirectAttributes.addFlashAttribute("activeTab", "files");
            redirectAttributes.addFlashAttribute("isSuccess", false);
            redirectAttributes.addFlashAttribute("errorType", 2);
        }
        else if(!fileUpload.isEmpty() && !fileExists){
            fileService.addFile(fileUpload, userId);
            redirectAttributes.addFlashAttribute("activeTab", "files");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("activeTab", "files");
            redirectAttributes.addFlashAttribute("isSuccess", false);
            redirectAttributes.addFlashAttribute("errorType", 1);
        }

//        fileService.addFile(fileUpload, userId);
//        model.addAttribute("fileList", fileService.getFilesByUserId(userId));
        return "redirect:/result";

    }

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity downloadFile(@PathVariable Integer fileId) {
        File file = fileService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        fileService.deleteByFileId(fileId);
        model.addAttribute("fileList", fileService.getFilesByUserId(userId));
        return "home";
    }
}