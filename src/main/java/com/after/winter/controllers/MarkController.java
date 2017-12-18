package com.after.winter.controllers;

import com.after.winter.model.Mark;
import com.after.winter.model.Note;
import com.after.winter.model.User;
import com.after.winter.services.MarkService;
import com.after.winter.services.NoteService;
import com.after.winter.services.UserService;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarkController {

  private final static String USER_BY_ID = "/user/{id}/";

  private final MarkService markService;
  private final UserService userService;
  private final NoteService noteService;

  @Autowired
  public MarkController(MarkService markService, UserService userService,
      NoteService noteService) {
    this.markService = markService;
    this.userService = userService;
    this.noteService = noteService;
  }

  @RequestMapping(value = (USER_BY_ID + "/marks"), method = RequestMethod.GET)
  @ResponseBody
  public List<Mark> getAllMarksByUserId(@PathVariable("id")Long userId) {
    return markService.getAllMarksByUserId(userId);
  }

  @RequestMapping(value = (USER_BY_ID + "/mark/{mark_id}"), method = RequestMethod.GET)
  @ResponseBody
  public Mark getMarkByIdAndUserId(@PathVariable("id")Long userId, @PathVariable("mark_id")
      Long markId) {
    return markService.getMarkByIdAndUserId(markId, userId);
  }

  @RequestMapping(value = (USER_BY_ID + "/mark"), method = RequestMethod.POST)
  @ResponseBody
  public String createMark(@PathVariable("id")Long userId, @RequestBody Mark mark) {
    User user = userService.getUser(userId);
    mark.setUser(user);
    Mark createdMark = markService.createMark(mark);
    if (createdMark != null) {
      return "Mark has been created with ID -" + createdMark.getId();
    } else {
      return "Failed to create Mark";
    }
  }

  @RequestMapping(value = (USER_BY_ID + "/mark"), method = RequestMethod.PUT)
  @ResponseBody
  public String updateMark(@PathVariable("id")Long userId, @RequestBody Mark mark) {
    mark.setUser(userService.getUser(userId));
    Mark updatedMark = markService.updateMark(mark);
    if (updatedMark != null) {
      return "Mark has been updated";
    } else {
      return "Failed to update mark";
    }
  }

  @RequestMapping(value = (USER_BY_ID + "/mark/{mark_id}"), method = RequestMethod.DELETE)
  @ResponseBody
  public String deleteMark(@PathVariable("id")Long userId, @PathVariable("mark_id")Long markId) {
    User user = userService.getUser(userId);
    Mark mark = markService.getMarkByIdAndUserId(markId, user.getId());
    if (user.getMarks().contains(mark))
    if (markService.deleteMark(markId)) {
      return "Mark has been deleted";
      //TODO: it's not work for now
    }
    return "Failed to delete";
  }

  @RequestMapping(value = USER_BY_ID + "mark/{mark_id}/notebook/{notebook_id}/note/{note_id}/set",
      method = RequestMethod.GET)
  @ResponseBody
  public String markNote(@PathVariable("id")Long userId, @PathVariable("mark_id")Long markId,
      @PathVariable("note_id")Long noteId, @PathVariable("notebook_id")Long notebookId) {
    Mark mark = markService.getMarkByIdAndUserId(markId, userId);
    Note note = noteService.getNoteByIdAndNotebookId(noteId, notebookId);
    if (noteService.addMarkToNote(mark, note)) {
      return "Mark has been apply to note";
    } else {
      return "Failed to mark note";
    }
  }

  @RequestMapping(value = USER_BY_ID + "mark/{mark_id}/notebook/{notebook_id}/note/{note_id}/remove",
      method = RequestMethod.GET)
  @ResponseBody
  public String removeMarkFromNote(@PathVariable("id")Long userId, @PathVariable("mark_id")Long markId,
      @PathVariable("note_id")Long noteId, @PathVariable("notebook_id")Long notebookId) {
    //TODO: not work =(
    Mark mark = markService.getMarkByIdAndUserId(markId, userId);
    Note note = noteService.getNoteByIdAndNotebookId(noteId, notebookId);
    if (noteService.removeMarkFromNote(mark, note)) {
      return "Mark has been removed from note";
    } else {
      return "Failed to remove mark from note";
    }
  }

  @RequestMapping(value = (USER_BY_ID + "mark/{mark_id}/show_notes"), method = RequestMethod.GET)
  @ResponseBody
  public List<Note> getAllNotesByMark(@PathVariable("id")Long userId,
      @PathVariable("mark_id")Long markId) {
    Mark mark = markService.getMarkByIdAndUserId(markId, userId);
    return markService.getNotesWithMarks(Arrays.asList(new Mark[]{mark}));

  }
}
