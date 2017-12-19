package com.after.winter.services.impl;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.repository.NoteRepository;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.services.NoteService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;

  @Autowired
  public NoteServiceImpl(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  @Override
  public Note getNoteById(Long id) {
    if (id != null) {
      return noteRepository.getOne(id);
    } else {
      return null;
    }
  }

  @Override
  public Note getNoteByIdAndNotebookId(Long noteId, Long notebookId) {
    if (noteId != null && notebookId != null) {
      return noteRepository.getByIdAndNotebookId(noteId, notebookId);
    }
    return null;
  }

  @Override
  public Note createNote(Note note) {
    if (note != null && note.getNotebook() != null) {
      return noteRepository.saveAndFlush(note);

    }
    return null;
  }

  @Override
  public Note updateNote(Note note) {
    if (note != null && noteRepository.existsById(note.getId())) {
      return noteRepository.saveAndFlush(note);

    }
    return null;
  }

  @Override
  public boolean deleteNote(Long noteId) {
    if (noteId != null && noteRepository.existsById(noteId)) {
      noteRepository.deleteById(noteId);
      return true;
    }
    return false;
  }

  @Override
  public List<Note> getAllNotesByNotebook(Notebook notebook) {
    if (notebook != null) {
      return noteRepository.getAllByNotebook(notebook);
    }
    return null;
  }


  @Override
  public boolean addMarkToNote(Mark mark, Note note) {
    if (mark != null && note != null && note.getNotebook() != null) {
      note.getMarks().add(mark);
      noteRepository.saveAndFlush(note);
      return true;
    }
    return false;
  }

  @Override
  public boolean removeMarkFromNote(Mark mark, Note note) {
    if (mark != null && note != null && note.getNotebook() != null) {
      note.getMarks().remove(mark);
      noteRepository.saveAndFlush(note);
      return true;
    }
    return false;
  }

  @Override
  public boolean removeAllMarksFromNote(Note note) {
    if (note != null && note.getNotebook() != null) {
      note.setMarks(new ArrayList<Mark>());
      noteRepository.saveAndFlush(note);
      return true;
    }
    return false;
  }
}
