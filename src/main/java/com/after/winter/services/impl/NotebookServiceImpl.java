package com.after.winter.services.impl;

import com.after.winter.model.Notebook;
import com.after.winter.repository.NotebookRepository;
import com.after.winter.services.NotebookService;

import java.util.Collections;
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
  public Notebook getNotebookByIdAndUserId(Long notebookId, Long userId) {
    if (notebookId != null && userId !=null) {
      return notebookRepository.getByIdAndUserId(notebookId, userId);
    }
    return null;
  }

  @Override
  public Notebook createNotebook(Notebook notebook) {

    if (notebook != null && notebook.getUser() != null) {
      return notebookRepository.saveAndFlush(notebook);
    }
    return null;
  }

  @Override
  public Notebook updateNotebook(Notebook notebook) {
    if (notebook != null && notebookRepository.existsById(notebook.getId())) {
      return notebookRepository.saveAndFlush(notebook);
    }
    return null;
  }


  @Override
  public boolean deleteNotebook(Long notebookId) {
    if (notebookId != null && notebookRepository.existsById(notebookId)) {
      notebookRepository.deleteById(notebookId);
      return true;
    }
    return false;
  }

  @Override
  public List<Notebook> getAllNotebooksByUserId(Long userId) {
    if (userId != null) {
      return notebookRepository.findAllByUserId(userId);
    }
    return Collections.emptyList();
  }

}
