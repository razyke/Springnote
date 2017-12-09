package com.after.winter.services;

import com.after.winter.model.Mark;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MarkService {

  Mark getMark(Long id);

  boolean createMark(Mark mark);

  boolean updateMark(Mark mark);

  boolean deleteMark(Long id);

  List<Mark> getAllMarks();

}
