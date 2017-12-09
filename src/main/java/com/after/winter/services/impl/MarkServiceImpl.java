package com.after.winter.services.impl;

import com.after.winter.model.Mark;
import com.after.winter.repository.MarkRepository;
import com.after.winter.services.MarkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkServiceImpl implements MarkService {

  private final MarkRepository markRepository;

  @Autowired
  public MarkServiceImpl(MarkRepository markRepository) {
    this.markRepository = markRepository;
  }

  public Mark getMark(Long id) {
    return markRepository.getOne(id);
   // if (markRepository.exists(id)) {
      //return markRepository.getById(id);
    //}
   // return null;
  }

  public boolean createMark(Mark mark) {
    if (mark != null) {
      markRepository.save(mark);
      return true;
    }
    return false;
  }

  public boolean updateMark(Mark mark) {
    if (mark != null && markRepository.exists(mark.getId())) {
      markRepository.save(mark);
      return true;
    }
    return false;
  }

  public boolean deleteMark(Long id) {
    if (markRepository.exists(id)) {
      markRepository.delete(id);
      return true;
    }
    return false;
  }

  public List<Mark> getAllMarks() {
    return markRepository.findAll();
  }
}
