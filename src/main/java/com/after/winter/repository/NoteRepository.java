package com.after.winter.repository;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  List<Note> getAllByNotebook(Notebook notebook);

  Note getByIdAndNotebookId(Long noteId, Long notebookId);

  List<Note> getAllByMarks(List<Mark> marks);
}
