package com.after.winter.repository;

import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  Note getByTitleAndAndNotebookId(String title, Long notebookId);

  List<Note> getAllByNotebook(Notebook notebook);

  Note getByTitle(String title);
}
