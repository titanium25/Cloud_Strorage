package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    String findByFileName = "SELECT * FROM FILES WHERE fileName = #{fileName}";
    String getByUserId = "SELECT * FROM FILES WHERE userId = #{userId}";
    String insert = "INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})";
    String deleteById = "DELETE FROM FILES WHERE fileId = #{fileId}";
    String getFileById = "SELECT * FROM FILES WHERE fileId = #{fileId}";
    String getFileNameByUserID = "SELECT * FROM FILES WHERE fileName = #{fileName} AND userId = #{userId}";

    @Select(findByFileName)
    File findByFileName(String fileName);

    @Select(getByUserId)
    List<File> getFileByUserId(Integer userId);

    // Search for duplicated file name per user
    @Select(getFileNameByUserID)
    File getFileNameByUserID(String fileName, Integer userId);

    @Select(getFileById)
    File getFileById(Integer fileId);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Delete(deleteById)
    void deleteByFileId(Integer fileId);
}