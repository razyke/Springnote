package com.after.winter.repository;

import com.after.winter.model.Notebook;
import com.after.winter.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook, Long> {

  List<Notebook> findAllByUser(User user);

  Notebook getByTitle(String title);
}
