package com.after.winter.services;

import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface NotebookService {

  Notebook getNotebook(Long id);

  Notebook getNotebookByTitle(String title);

  boolean createNotebook(Notebook notebook);

  boolean updateNotebook(Notebook notebook);

  boolean deleteNotebook(Notebook notebook);

  List<Notebook> getAllNotebooksByUser(User user);

}
