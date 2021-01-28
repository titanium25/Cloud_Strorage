package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

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
            redirectAttributes.addFlashAttribute("errorText", "File " + fileUpload.getOriginalFilename() + " already exists. Please rename or choose a different file to upload.");
        } else {
            fileService.addFile(fileUpload, userId);
            redirectAttributes.addFlashAttribute("isSuccess", true);
            redirectAttributes.addFlashAttribute("errorText", "File " + fileUpload.getOriginalFilename() + " was successfully uploaded");
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
        File file = fileService.getFileById(fileId);
        fileService.deleteByFileId(fileId);
        redirectAttributes.addFlashAttribute("isSuccess", true);
        redirectAttributes.addFlashAttribute("errorText", "File " + file.getFileName() + " was successfully deleted");
        redirectAttributes.addFlashAttribute("activeTab", "files");
        return "redirect:/result";
    }
}