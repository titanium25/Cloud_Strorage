package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    String getAll = "SELECT * FROM CREDENTIALS";
    String getByNoteId = "SELECT * FROM CREDENTIALS WHERE noteid = #{noteid}";
    String getByUserId = "SELECT * FROM CREDENTIALS WHERE userid = #{userid}";
    String update = "UPDATE CREDENTIALS SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}";
    String insert = "INSERT INTO CREDENTIALS (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})";
    String deleteById = "DELETE from CREDENTIALS WHERE noteid = #{noteid}";


    @Select(getByNoteId)
    List<Note> getNoteByNoteId(Integer id);

    @Select(getByUserId)
    List<Note> getNoteByUserId(Integer id);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    Credential[] getCredentialListings(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, password = #{password}, username = #{newUserName} WHERE credentialid = #{credentialId}")
    void updateCredential(Integer credentialId, String newUserName, String url, String key, String password);
}