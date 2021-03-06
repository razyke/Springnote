package com.after.winter.services.impl;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.repository.MarkRepository;
import com.after.winter.repository.NoteRepository;
import com.after.winter.services.MarkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarkServiceImpl implements MarkService {

  private final MarkRepository markRepository;
  private final NoteRepository noteRepository;

  @Autowired
  public MarkServiceImpl(MarkRepository markRepository,
      NoteRepository noteRepository) {
    this.markRepository = markRepository;
    this.noteRepository = noteRepository;
  }


  @Override
  public Mark getMarkByIdAndUserId(Long markId, Long userId) {
    if (markId != null && userId != null) {
      return markRepository.getByIdAndUserId(markId, userId);
    }
    return null;
  }

  @Override
  public List<Note> getNotesWithMarks(List<Mark> marks) {
    if (marks != null && !marks.isEmpty()) {
      return noteRepository.getAllByMarks(marks);
    }
    return null;
  }

  @Override
  public Mark createMark(Mark mark) {
    if (mark != null && mark.getUser() != null) {
      return markRepository.saveAndFlush(mark);
    }
    return null;
  }

  @Override
  public Mark updateMark(Mark mark) {
    if (mark != null && mark.getUser() != null && markRepository.existsById(mark.getId())) {
      return markRepository.saveAndFlush(mark);
    }
    return null;
  }


  @Override
  public boolean deleteMark(Mark mark) {
    if (mark != null && markRepository.existsById(mark.getId())) {
      markRepository.delete(mark);
      return true;
    }
    return false;
  }

  @Override
  public List<Mark> getAllMarksByUserId(Long userId) {
    if (userId != null) {
      return markRepository.getAllByUserId(userId);
    }
    else return null;
  }

}
