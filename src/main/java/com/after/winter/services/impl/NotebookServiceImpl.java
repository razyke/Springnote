package com.after.winter.services.impl;

import com.after.winter.model.Notebook;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.services.NotebookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotebookServiceImpl implements NotebookService {

  private final NotebookRepository notebookRepository;

  @Autowired
  public NotebookServiceImpl(NotebookRepository notebookRepository) {
    this.notebookRepository = notebookRepository;
  }

  @Override
  public Notebook getNotebook(Long id) {
    if (id != null) {
      return notebookRepository.findOne(id);
    }
    return null;
  }

  @Override
  public Notebook getNotebookByTitleAndUserId(String title, Long userId) {
    if (title != null && userId != null && !title.isEmpty()) {
      return notebookRepository.getByTitleAndUserId(title, userId);
    }
    return null;
  }

  @Override
  public boolean createNotebook(Notebook notebook) {

    if (notebook != null && notebook.getUser() != null) {
      notebookRepository.saveAndFlush(notebook);
      return true;
    }
    return false;
  }

  @Override
  public boolean updateNotebook(Notebook notebook) {
    if (notebookRepository.exists(notebook.getId())) {
      notebookRepository.saveAndFlush(notebook);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteNotebook(Long notebookId) {
    if (notebookId != null && notebookRepository.exists(notebookId)) {
      notebookRepository.delete(notebookId);
      return true;
    }
    return false;
  }

  @Override
  public List<Notebook> getAllNotebooksByUserId(Long userId) {
    return notebookRepository.findAllByUserId(userId);
  }

}
