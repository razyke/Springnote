package com.after.winter.services;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MarkService {

  Mark getMark(Long id);

  Mark getMarkByType(String type);

  boolean createMark(Mark mark);

  boolean updateMark(Mark mark);

  boolean deleteMarkFromNote(Note note, Mark mark);

  boolean deleteMark(Mark mark);

  List<Mark> getAllMarks();

}
