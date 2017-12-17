package com.after.winter.services;

import com.after.winter.model.Notebook;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface NotebookService {

  Notebook getNotebook(Long id);

  Notebook getNotebookByIdAndUserId(Long notebookId, Long userId);

  Notebook getNotebookByTitleAndUserId(String title, Long userId);

  Notebook createNotebook(Notebook notebook);

  Notebook updateNotebook(Notebook notebook);

  boolean deleteNotebook(Long notebookId);

  List<Notebook> getAllNotebooksByUserId(Long userId);

}
