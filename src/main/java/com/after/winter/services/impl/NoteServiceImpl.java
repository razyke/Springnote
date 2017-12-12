package com.after.winter.services.impl;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.repository.MarkRepository;
import com.after.winter.repository.NoteRepository;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.repository.UserRepository;
import com.after.winter.services.NoteService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;

  @Autowired
  public NoteServiceImpl(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  @Override
  public Note getNote(Long id) {
    if (id != null) {
      return noteRepository.getOne(id);
    }
    return null;
  }

  @Override
  public Note getNoteByTitle(String title) {
    if (title != null && !title.isEmpty()) {
      return noteRepository.getByTitle(title);
    }
    return null;
  }

  @Override
  public Note getNoteByTitleAndNotebookId(String title, Long notebookId) {
    if (title!= null && notebookId != null && !title.isEmpty()) {
      return noteRepository.getByTitleAndAndNotebookId(title, notebookId);
    }
    return null;
  }

  @Override
  @Transactional
  public boolean createNote(Note note) {
    if (note != null && note.getNotebook() != null) {
      noteRepository.saveAndFlush(note);
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public boolean updateNote(Note note) {
    if (note != null && noteRepository.exists(note.getId())) {
      noteRepository.saveAndFlush(note);
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public boolean deleteNote(Long noteId) {
    if (noteId != null && noteRepository.exists(noteId)) {
      noteRepository.delete(noteId);
      return true;
    }
    return false;
  }

  @Override
  public List<Note> getAllNotes() {
    return noteRepository.findAll();
  }

  @Override
  public List<Note> getAllNotesByNotebook(Notebook notebook) {
    if (notebook != null) {
      return noteRepository.getAllByNotebook(notebook);
    }
    return null;
  }

  @Override
  public List<Note> getAllNotesByTag(Mark mark, User user) {
  /*  List<Notebook> notebooks = notebookRepository.findAllByUser(user);
  //TODO: Implement normal
    List<Note> notes = new ArrayList<>();
    for (Notebook notebook : notebooks) {
      for (Note n : notebook.getNotes()) {
        for (Mark m : n.getMarks()) {
          if (m.getType().equals(mark.getType())) {
            notes.add(n);
          }
        }
      }
    }
    return notes;*/
  return null;
  }

  @Override
  @Transactional
  public boolean addMarkToNote(Mark mark, Note note) {
    if (mark != null && note != null) {
      note.getMarks().add(mark);
      noteRepository.saveAndFlush(note);
      return true;
    }
    return false;
  }
}
