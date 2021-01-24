package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getFileByUserId(userId);
    }

    public void addFile(MultipartFile multipartFile, Integer userId) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        byte[] bytes = multipartFile.getBytes();
        File file = new File(0, fileName, contentType, fileSize, userId, bytes);

        fileMapper.insert(file);
    }

    public void deleteByFileId(Integer fileId){
        fileMapper.deleteFile(fileId);
    }

    public File findByFileName(String fileName){
        return fileMapper.findByFileName(fileName);
    }

    public File downloadFile(Integer fileId){
        return fileMapper.getFile(fileId);
    }

    public boolean fileExists(String fileName, Integer userId){
        return fileMapper.getFileNameByUserID(fileName, userId) != null;
    }
}
