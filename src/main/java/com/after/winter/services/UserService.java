package com.after.winter.services;

import com.after.winter.model.User;
import java.util.List;

public interface UserService {

  User getUser(Long id);

  boolean createUser(User user);

  boolean updateUser(User user);

  boolean deleteUser(Long id);

  List<User> getAllUsers();

}
