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

  Note getNoteByTitle(String title);

  Note getNoteByTitleAndNotebookId(String title, Long NotebookId);

  boolean createNote(Note note);

  boolean updateNote(Note note);

  boolean deleteNote(Long noteId);

  List<Note> getAllNotes();

  List<Note> getAllNotesByNotebook(Notebook notebook);

  List<Note> getAllNotesByTag(Mark mark, User user);

  boolean addMarkToNote(Mark mark, Note note);

}
