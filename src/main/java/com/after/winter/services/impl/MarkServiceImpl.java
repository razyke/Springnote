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
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarkServiceImpl implements MarkService {

  private final MarkRepository markRepository;

  @Autowired
  public MarkServiceImpl(MarkRepository markRepository) {
    this.markRepository = markRepository;
  }

  @Override
  public Mark getMark(Long id) {
    if (markRepository.exists(id)) {
      return markRepository.getOne(id);
    }
    return null;
  }

  @Override
  public Mark getMarkByTypeAndUserId(String type, Long userId) {
    if (type != null && userId != null && !type.isEmpty()) {
      return markRepository.getByTypeAndUserId(type, userId);
    }
    return null;
  }

  @Override
  @Transactional
  public boolean createMark(Mark mark) {
    if (mark != null && mark.getUser() != null) {
      markRepository.saveAndFlush(mark);
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public boolean updateMark(Mark mark) {
    if (mark != null && markRepository.exists(mark.getId())) {
      markRepository.saveAndFlush(mark);
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public boolean deleteMarkFromNote(Note note, Mark mark) {
    if (note != null && mark != null) {
      mark.getNotes().add(note);
      markRepository.saveAndFlush(mark);
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public boolean deleteMark(Long markId) {
    if (markRepository.exists(markId)) {
      markRepository.delete(markId);
      return true;
    }
    return false;
  }

  @Override
  public List<Mark> getAllMarks() {
    return markRepository.findAll();
  }
}
