package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    String getUserByUserName = "SELECT * FROM USERS WHERE userName = #{userName}";
    String insert = "INSERT INTO USERS (userName, salt, password, firstName, lastName) VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName})";
    String update = "UPDATE USERS SET userName = #{userName}, firstName = #{firstName}, lastName = #{lastName} WHERE userId = #{userId}";

    @Select(getUserByUserName)
    User getUser(String userName);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Update(update)
    int updateNote(Integer noteId, String noteTitle, String noteDescription);
}