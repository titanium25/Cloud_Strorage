package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note){
        Note newNote = new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), note.getUserId());
        return noteMapper.insert(newNote);
    }

    public List<Note> getAllNotes(){
        return noteMapper.getAllNotes();
    }

    public List<Note> getNotesByUserId(Integer userId){
        return noteMapper.getNoteByUserId(userId);
    }

    public List<Note> getNotesByNoteId(Integer noteId){
        return noteMapper.getNoteByNoteId(noteId);
    }

    public int updateNote(Integer noteId, String noteTitle, String noteDescription){
        return noteMapper.updateNote(noteId,noteTitle,noteDescription);
    }

    public void deleteNote(Integer id){
        noteMapper.delete(id);
    }
}
