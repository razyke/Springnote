package com.after.winter.repository;

import com.after.winter.model.Mark;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

  Mark getByType(String type);

}
