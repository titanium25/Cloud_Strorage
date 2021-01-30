package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    String getUserByUserName = "SELECT * FROM USERS WHERE userName = #{userName}";
    String insert = "INSERT INTO USERS (userName, salt, password, firstName, lastName, email) VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName}, #{email})";
    String update = "UPDATE USERS SET userName = #{userName}, password = #{password}, firstName = #{firstName}, lastName = #{lastName}, email = #{email} WHERE userId = #{userId}";

    @Select(getUserByUserName)
    User getUser(String userName);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insert(User user);

    @Update(update)
    int updateUser(User user);
}