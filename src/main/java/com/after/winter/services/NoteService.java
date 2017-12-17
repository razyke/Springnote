package com.after.winter.services;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface NoteService {

  Note getNote(Long id);

  Note getNoteByTitleAndNotebookId(String title, Long NotebookId);

  Note createNote(Note note);

  Note updateNote(Note note);

  boolean deleteNote(Long noteId);

  List<Note> getAllNotes();

  List<Note> getAllNotesByNotebook(Notebook notebook);

  boolean addMarkToNote(Mark mark, Note note);

  boolean removeMarkFromNote(Mark mark, Note note);

  boolean removeAllMarksFromNote(Note note);

}
