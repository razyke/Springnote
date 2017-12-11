package com.after.winter.repository;

import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

  Note getByTitle(String title);

  List<Note> getAllByNotebook(Notebook notebook);
}
