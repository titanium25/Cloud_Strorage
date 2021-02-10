package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    String getAllByUserId = "SELECT * FROM CREDENTIALS WHERE userId = #{userId}";
    String insert = "INSERT INTO CREDENTIALS (url, userName, key, password, userId) VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})";
    String deleteByCredentialId = "DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}";
    String update = "UPDATE CREDENTIALS SET url = #{url}, userName = #{userName}, key = #{key}, password = #{password} WHERE credentialId = #{credentialId}";

    // show all credentials in home page
    @Select(getAllByUserId)
    List<Credential> getAllByUserId(Integer userId);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);

    @Delete(deleteByCredentialId)
    void deleteByCredentialId(Integer credentialId);

    @Update(update)
    Integer updateCredential(Credential credential);
}