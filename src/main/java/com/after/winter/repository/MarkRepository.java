package com.after.winter.repository;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

  List<Mark> getAllByUserId(Long userId);

  Mark getByIdAndUserId(Long markId, Long userId);

}
