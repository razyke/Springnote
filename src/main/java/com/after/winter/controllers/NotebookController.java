package com.after.winter.controllers;

import com.after.winter.model.Notebook;
import com.after.winter.services.NotebookService;
import com.after.winter.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotebookController {

  private final static String USER_BY_ID = "/user/{id}";

  private final UserService userService;
  private final NotebookService notebookService;

  @Autowired
  public NotebookController(UserService userService,
      NotebookService notebookService) {
    this.userService = userService;
    this.notebookService = notebookService;
  }

  @RequestMapping(value = (USER_BY_ID + "/notebooks"), method = RequestMethod.GET)
  @ResponseBody
  public List<Notebook> getAllNotebooksByUserId(@PathVariable("id")Long userId) {
    return notebookService.getAllNotebooksByUserId(userId);
  }

  @RequestMapping(value = (USER_BY_ID + "/notebook/{notebook_id}"), method = RequestMethod.GET)
  @ResponseBody
  public Notebook getNotebookByUserIdAndNotebookId(@PathVariable("id")Long userId,
                                                   @PathVariable("notebook_id")Long id) {
    return notebookService.getNotebookByIdAndUserId(id, userId);
  }

  @RequestMapping(value = (USER_BY_ID + "/notebook"), method = RequestMethod.POST)
  @ResponseBody
  public void createNotebookForUser(@PathVariable("id")Long userId) {

  }


}
