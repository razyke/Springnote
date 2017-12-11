package com.after.winter.services.impl;

import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.repository.NoteRepository;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.repository.UserRepository;
import com.after.winter.services.NotebookService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotebookServiceImpl implements NotebookService {

  private final NoteRepository noteRepository;
  private final NotebookRepository notebookRepository;
  private final UserRepository userRepository;

  @Autowired
  public NotebookServiceImpl(NoteRepository noteRepository,
      NotebookRepository notebookRepository,
      UserRepository userRepository) {
    this.noteRepository = noteRepository;
    this.notebookRepository = notebookRepository;
    this.userRepository = userRepository;
  }

  public Notebook getNotebook(Long id) {
    if (id != null) {
      return notebookRepository.findOne(id);
    }
    return null;
  }

  @Override
  public Notebook getNotebookByTitle(String title) {
    if (title != null && !title.isEmpty()) {
      return notebookRepository.getByTitle(title);
    }
    return null;
  }

  public boolean createNotebook(Notebook notebook) {
    if (notebook != null && notebook.getUser() != null) {
      User user = notebook.getUser();
      if (user.getNotebooks() == null || user.getNotebooks().isEmpty()) {
        user.setNotebooks(new ArrayList<>(Arrays.asList(notebook)));
      } else {
        user.getNotebooks().add(notebook);
      }
      notebookRepository.save(notebook);
      userRepository.save(user);
      return true;
    }
    return false;
  }

  public boolean updateNotebook(Notebook notebook) {
    if (notebookRepository.exists(notebook.getId())) {
      User user = notebook.getUser();
      List<Notebook> notebooks = user.getNotebooks();
      for (int i = 0; i < notebooks.size(); i++) {
        if (notebooks.get(i).getId().equals(notebook.getId())) {
          notebooks.set(i, notebook);
        }
      }
      notebookRepository.save(notebook);
      userRepository.save(user);
      return true;
    }
    return false;
  }

  public boolean deleteNotebook(Notebook notebook) {
    if (notebookRepository.exists(notebook.getId())) {
      User user = userRepository.getUserByEmail(notebook.getUser().getEmail());
      List<Notebook> notebooks = user.getNotebooks();
      for (int i = 0; i < notebooks.size(); i++) {
        if (notebooks.get(i).getId().equals(notebook.getId())) {
          notebooks.remove(i);
        }
      }
      user.setNotebooks(notebooks);
      userRepository.save(user);
      noteRepository.delete(notebook.getNotes());
      notebookRepository.delete(notebook);
      return true;
    }
    return false;
  }

  public List<Notebook> getAllNotebooksByUser(User user) {
    return notebookRepository.findAllByUser(user);
  }
}
