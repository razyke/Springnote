package com.after.winter.services;

import com.after.winter.model.Notebook;
import java.util.List;

public interface NotebookService {

  Notebook getNotebook(Long id);

  boolean createNotebook(Notebook notebook);

  boolean updateNotebook(Notebook notebook);

  boolean deleteNotebook(Long id);

  List<Notebook> getAllNotebooks();

}
