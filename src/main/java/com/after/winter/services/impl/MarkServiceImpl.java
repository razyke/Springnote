package com.after.winter.services.impl;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.repository.MarkRepository;
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
    if (id != null && markRepository.exists(id)) {
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
  @Transactional
  public Mark createMark(Mark mark) {
    if (mark != null) {
      return markRepository.saveAndFlush(mark);

    }
    return null;
  }

  @Override
  @Transactional
  public Mark updateMark(Mark mark) {
    if (mark != null && markRepository.exists(mark.getId())) {
      return markRepository.saveAndFlush(mark);

    }
    return null;
  }


  @Override
  @Transactional
  public boolean deleteMark(Long markId) {
    if (markId != null && markRepository.exists(markId)) {
      markRepository.delete(markId);
      return true;
    }
    return false;
  }

}
