package com.after.winter.controllers;

import com.after.winter.model.User;
import com.after.winter.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  @ResponseBody
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  @ResponseBody
  public User getUserById(@PathVariable("id")Long id) {
    return userService.getUser(id);
  }

  @RequestMapping(value = "/user",method = RequestMethod.POST)
  public void createUser(@RequestBody User user) {
    userService.createUser(user);
  }

  @RequestMapping(value = "/user",method = RequestMethod.PUT)
  public void updateUser(@RequestBody User user) {
    userService.updateUser(user);
  }
}
