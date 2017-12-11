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

@Service
public class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;
  private final NotebookRepository notebookRepository;
  private final MarkRepository markRepository;
  private final UserRepository userRepository;

  @Autowired
  public NoteServiceImpl(NoteRepository noteRepository,
      MarkRepository markRepository,
      NotebookRepository notebookRepository,
      UserRepository userRepository) {
    this.noteRepository = noteRepository;
    this.notebookRepository = notebookRepository;
    this.markRepository = markRepository;
    this.userRepository = userRepository;
  }

  public Note getNote(Long id) {
    if (id != null) {
      return noteRepository.getOne(id);
    }
    return null;
  }

  @Override
  public Note getNoteByTitle(String title) {
    if (title!= null && !title.isEmpty()) {
      return noteRepository.getByTitle(title);
    }
    return null;
  }

  public boolean createNote(Note note) {
    if (note != null && note.getNotebook() != null) {
      Notebook notebook = note.getNotebook();
      if (notebook.getNotes() == null || notebook.getNotes().isEmpty()) {
        notebook.setNotes(new ArrayList<>(Arrays.asList(note)));
      } else {
        notebook.getNotes().add(note);
      }
      noteRepository.save(note);
      notebookRepository.save(notebook);
      return true;
    }
    return false;
  }

  public boolean updateNote(Note note) {
    if (note != null && noteRepository.exists(note.getId())) {
      Notebook notebook = note.getNotebook();
      List<Note> notes = notebook.getNotes();
      for (int i = 0; i < notes.size(); i++) {
        if (note.getId().equals(notes.get(i).getId())) {
          notes.set(i, note);
        }
      }
      noteRepository.save(note);
      notebookRepository.save(notebook);
      return true;
    }
    return false;
  }

  public boolean deleteNote(Note note) {

    if (note != null && noteRepository.exists(note.getId())) {
      List<Mark> marks = note.getMarks();

      for (int i = 0; i < marks.size(); i++) {
        for (int j = 0; j < marks.get(i).getNotes().size(); j++) {
          if (marks.get(i).getNotes().get(j).getId().equals(note.getId())) {
            marks.get(i).getNotes().remove(j);
          }
        }
      }
      Notebook notebook = note.getNotebook();
      for (int i = 0; i < notebook.getNotes().size(); i++) {
        if (notebook.getNotes().get(i).getId().equals( note.getId())) {
          notebook.getNotes().remove(i);
        }
      }
      User user = notebook.getUser();
      for (int i = 0; i < user.getNotebooks().size(); i++) {
        if (user.getNotebooks().get(i).getId().equals(notebook.getId())) {
          user.getNotebooks().set(i, notebook);
        }
      }
      userRepository.save(user);
      notebookRepository.save(notebook);
      markRepository.save(marks);
      noteRepository.delete(note);
      return true;
    }
    return false;
  }

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
    List<Notebook> notebooks = notebookRepository.findAllByUser(user);
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
    return notes;
  }

  @Override
  public boolean addMarkToNote(Mark mark, Note note) {
    if (mark != null && note != null) {

      //better user SET for marks...
      if (note.getMarks() == null || note.getMarks().isEmpty()) {
        note.setMarks(new ArrayList<>(Arrays.asList(mark)));
      } else {
        note.getMarks().add(mark);
      }

      if (mark.getNotes() == null || mark.getNotes().isEmpty()) {
        mark.setNotes(new ArrayList<>(Arrays.asList(note)));
      } else {
        mark.getNotes().add(note);
      }

      Notebook notebook = note.getNotebook();
      List<Note> notes = notebook.getNotes();
      for (int i = 0; i < notes.size(); i++) {
        if (note.getId().equals(notes.get(i).getId())) {
          notes.set(i, note);
        }
      }

      User user = notebook.getUser();
      List<Notebook> notebooks = user.getNotebooks();
      for (int i = 0; i < notebooks.size(); i++) {
        if (notebook.getId().equals(notebooks.get(i).getId())) {
          notebooks.set(i, notebook);
        }
      }
      noteRepository.save(note);
      markRepository.save(mark);
      notebookRepository.save(notebook);
      userRepository.save(user);
      return true;
    }
    return false;
  }
}
