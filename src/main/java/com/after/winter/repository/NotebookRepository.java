package com.after.winter.repository;

import com.after.winter.model.Notebook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {

  List<Notebook> findAllByUserId(Long id);

  Notebook getByTitleAndUserId(String title, Long userId);

  Notebook getByIdAndUserId(Long noteId, Long userId);
}
