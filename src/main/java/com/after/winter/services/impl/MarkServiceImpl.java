package com.after.winter.services.impl;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.repository.MarkRepository;
import com.after.winter.repository.NoteRepository;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.repository.UserRepository;
import com.after.winter.services.MarkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkServiceImpl implements MarkService {

  private final UserRepository userRepository;
  private final NotebookRepository notebookRepository;
  private final NoteRepository noteRepository;
  private final MarkRepository markRepository;

  @Autowired
  public MarkServiceImpl(UserRepository userRepository,
      NotebookRepository notebookRepository,
      NoteRepository noteRepository,
      MarkRepository markRepository) {
    this.userRepository = userRepository;
    this.notebookRepository = notebookRepository;
    this.noteRepository = noteRepository;
    this.markRepository = markRepository;
  }

  public Mark getMark(Long id) {
    if (markRepository.exists(id)) {
      return markRepository.getOne(id);
    }
    return null;
  }

  @Override
  public Mark getMarkByType(String type) {
    if (type != null && !type.isEmpty()) {
      return markRepository.getByType(type);
    }
    return null;
  }

  @Override
  public boolean createMark(Mark mark) {
    if (mark != null) {
      markRepository.save(mark);
      return true;
    }
    return false;
  }

  @Override
  public boolean updateMark(Mark mark) {
    if (mark != null && markRepository.exists(mark.getId())) {
      markRepository.save(mark);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteMarkFromNote(Note note, Mark mark) {
    if (note != null && mark != null) {

      List<Note> notes = mark.getNotes();
      List<Mark> marks = note.getMarks();
      for (int i = 0; i < marks.size(); i++) {
        if (marks.get(i).getId().equals(mark.getId())) {
          marks.remove(i);
        }
      }
      for (int i = 0; i < notes.size(); i++) {
        if (notes.get(i).getId().equals(note.getId())) {
          notes.remove(i);
        }
      }

      Notebook notebook = note.getNotebook();
      User user = notebook.getUser();
      notebook.setNotes(notes);
      for (int i = 0; i < user.getNotebooks().size(); i++) {
        if (user.getNotebooks().get(i).getId().equals(note.getId())) {
          user.getNotebooks().set(i, notebook);
        }
      }
      userRepository.save(user);
      notebookRepository.save(notebook);
      markRepository.save(marks);
      noteRepository.save(notes);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteMark(Mark mark) {
    if (markRepository.exists(mark.getId())) {
      List<Note> notes = mark.getNotes();

      for (int i = 0; i < notes.size(); i++) {
        for (int j = 0; j < notes.get(i).getMarks().size(); j++) {
          if (notes.get(i).getMarks().get(j).getId().equals(mark.getId())) {
            notes.get(i).getMarks().remove(j);
          }
        }
      }

      //TODO: user nad notebook remove marks too...now go to sleep....
      noteRepository.save(notes);
      markRepository.delete(mark);
      return true;
    }
    return false;
  }

  @Override
  public List<Mark> getAllMarks() {
    return markRepository.findAll();
  }
}
