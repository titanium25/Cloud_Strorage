package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    String getAll = "SELECT * FROM NOTES";
    String getByNoteId = "SELECT * FROM NOTES WHERE noteid = #{noteid}";
    String getByUserId = "SELECT * FROM NOTES WHERE userid = #{userid}";
    String update = "UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}";
    String insert = "INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})";
    String deleteById = "DELETE from NOTES WHERE noteid = #{noteid}";


    @Select(getAll)
    List<Note> getAllNotes();

    @Select(getByNoteId)
    List<Note> getNoteByNoteId(Integer id);

    @Select(getByUserId)
    List<Note> getNoteByUserId(Integer id);


    @Update(update)
    int update(Note notes);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note notes);

    @Delete(deleteById)
    void delete(int id);
}
