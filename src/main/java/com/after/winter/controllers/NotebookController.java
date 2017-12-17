package com.after.winter.controllers;

import com.after.winter.model.Notebook;
import com.after.winter.services.NotebookService;
import com.after.winter.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping(value = "/notebooks")
public class NotebookController {

  private final UserService userService;
  private final NotebookService notebookService;

 // @Autowired
  public NotebookController(UserService userService,
      NotebookService notebookService) {
    this.userService = userService;
    this.notebookService = notebookService;
  }

//  @GetMapping(value = "/users/{id}/notebooks")
  public List<Notebook> getAllNotebooksByUserId(@PathVariable("id") Long userId) {
    return notebookService.getAllNotebooksByUserId(userId);
  }


}
