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

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialListings(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key, password, userId) " +
            "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, password = #{password}, userName = #{userName} WHERE credentialId = #{credentialId}")
    void updateCredential(Integer credentialId, String userName, String url, String key, String password);
}