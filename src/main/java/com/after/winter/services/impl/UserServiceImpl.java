package com.after.winter.services.impl;

import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import com.after.winter.repository.MarkRepository;
import com.after.winter.repository.NoteRepository;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.repository.UserRepository;
import com.after.winter.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final MarkRepository markRepository;
  private final NotebookRepository notebookRepository;
  private final NoteRepository noteRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
      MarkRepository markRepository,
      NotebookRepository notebookRepository,
      NoteRepository noteRepository) {
    this.userRepository = userRepository;
    this.markRepository = markRepository;
    this.notebookRepository = notebookRepository;
    this.noteRepository = noteRepository;
  }

  @Override
  public User getUserByEmail(String email) {
    if (email != null && !email.isEmpty()) {
      return userRepository.getUserByEmail(email);
    }
    return null;
  }

  public User getUser(Long id) {
    if (id != null) {
      return userRepository.findOne(id);
    }
    return null;
  }

  public boolean createUser(User user) {
    if (user != null) {
      userRepository.save(user);
      return true;
    }
    return false;
  }

  public boolean updateUser(User user) {
    if (user != null && userRepository.exists(user.getId())) {
      userRepository.save(user);
      return true;
    }
    return false;
  }

  public boolean deleteUser(User user) {
    if (user != null && userRepository.exists(user.getId())) {

      for (Notebook notebook : user.getNotebooks()) {
        if (notebook.getNotes() != null) {
          for (Note note : notebook.getNotes()) {
            if (note.getMarks() != null) {
              markRepository.delete(note.getMarks());
            }
          }
          noteRepository.delete(notebook.getNotes());
        }
      }
      notebookRepository.delete(user.getNotebooks());
      userRepository.delete(user);
      return true;
    }
    return false;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}
