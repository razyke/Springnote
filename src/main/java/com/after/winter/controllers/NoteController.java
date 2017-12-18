package com.after.winter.controllers;

import com.after.winter.model.Note;
import com.after.winter.model.Notebook;
import com.after.winter.services.NoteService;
import com.after.winter.services.NotebookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

  private final static String USER_BY_ID_NOTE_BY_ID = "/user/{id}/notebook/{notebook_id}";

  private final NotebookService notebookService;
  private final NoteService noteService;

  @Autowired
  public NoteController(NotebookService notebookService,
      NoteService noteService) {
    this.notebookService = notebookService;
    this.noteService = noteService;
  }

  @RequestMapping(value = (USER_BY_ID_NOTE_BY_ID + "/notes"), method = RequestMethod.GET)
  @ResponseBody
  public List<Note> getAllNotesByUserId(@PathVariable("id")Long userId, @PathVariable("notebook_id")
      Long notebookId) {
    Notebook notebook = notebookService.getNotebookByIdAndUserId(notebookId, userId);
    return noteService.getAllNotesByNotebook(notebook);
  }

  @RequestMapping(value = (USER_BY_ID_NOTE_BY_ID + "/note/{note_id}"), method = RequestMethod.GET)
  @ResponseBody
  public Note getNoteByNotebookIdAndUserId(@PathVariable("id")Long userId, @PathVariable("notebook_id")
      Long notebookId, @PathVariable("note_id")Long noteId) {
    Notebook notebook = notebookService.getNotebookByIdAndUserId(notebookId, userId);
    if (notebook != null) {
      Note note = noteService.getNoteByIdAndNotebookId(noteId, notebookId);
      if (notebook.getNotes().contains(note)) {
        return note;
      }
    }
    return null;
  }

  @RequestMapping(value = (USER_BY_ID_NOTE_BY_ID + "/note"), method = RequestMethod.POST)
  @ResponseBody
  public String createNoteByNotebookAndUserId(@PathVariable("id")Long userId, @PathVariable("notebook_id")
      Long notebookId, @RequestBody Note note) {
    Notebook notebook = notebookService.getNotebookByIdAndUserId(notebookId, userId);
    if (notebook != null) {
      note.setNotebook(notebook);
      Note created = noteService.createNote(note);
      return "Note has been created with ID - " + created.getId();
    }
    return "Failed to create note";
  }

  @RequestMapping(value = (USER_BY_ID_NOTE_BY_ID + "/note"), method = RequestMethod.PUT)
  @ResponseBody
  public String updateNoteByNotebookAndUserId(@PathVariable("id")Long userId, @PathVariable("notebook_id")
      Long notebookId, @RequestBody Note note) {
    Notebook notebook = notebookService.getNotebookByIdAndUserId(notebookId, userId);
    if (notebook != null) {
      note.setNotebook(notebook);
      noteService.updateNote(note);
      return "Note updated";
    }
    return "Failed to update note";
  }

  @RequestMapping(value = (USER_BY_ID_NOTE_BY_ID + "/note/{note_id}"), method = RequestMethod.DELETE)
  @ResponseBody
  public String deleteNoteByNotebookAndUserId(@PathVariable("id")Long userId, @PathVariable("notebook_id")
      Long notebookId, @PathVariable("note_id")Long noteId) {
    Notebook notebook = notebookService.getNotebookByIdAndUserId(notebookId, userId);
    if (notebook != null) {
      Note note = noteService.getNoteByIdAndNotebookId(noteId, notebookId);
      if (notebook.getNotes().contains(note)) {
        noteService.deleteNote(noteId);
      }
        return "Note delete success";
    }
    return "Failed to delete note";
  }
}
