package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    String getAll = "SELECT * FROM NOTES";
    String getByNoteId = "SELECT * FROM NOTES WHERE noteId = #{noteId}";
    String getByUserId = "SELECT * FROM NOTES WHERE userId = #{userId}";
    String update = "UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}";
    String insert = "INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})";
    String deleteById = "DELETE from NOTES WHERE noteId = #{noteId}";


    @Select(getAll)
    List<Note> getAllNotes();

    @Select(getByNoteId)
    List<Note> getNoteByNoteId(Integer id);

    @Select(getByUserId)
    List<Note> getNoteByUserId(Integer id);

    @Update(update)
    Integer updateNote(Integer noteId, String noteTitle, String noteDescription);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note notes);

    @Delete(deleteById)
    void delete(int id);
}
