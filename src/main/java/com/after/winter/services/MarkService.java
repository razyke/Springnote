package com.after.winter.services;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

public interface MarkService {

  Mark getMarkByIdAndUserId(Long markId, Long userId);

  List<Note> getNotesWithMarks(List<Mark> marks);

  Mark createMark(Mark mark);

  Mark updateMark(Mark mark);

  boolean deleteMark(Mark mark);

  List<Mark> getAllMarksByUserId(Long userId);

}
