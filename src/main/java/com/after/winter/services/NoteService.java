package com.after.winter.services;

import com.after.winter.model.Note;
import java.util.List;

public interface NoteService {

  Note getNote(Long id);

  boolean createNote(Note note);

  boolean updateNote(Note note);

  boolean deleteNote(Long id);

  List<Note> getAllNotes();

}
