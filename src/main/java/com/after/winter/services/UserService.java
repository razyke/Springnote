package com.after.winter.services;

import com.after.winter.model.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

  User getUserByEmail(String email);

  User getUser(Long id);

  boolean createUser(User user);

  boolean updateUser(User user);

  boolean deleteUser(Long userId);

  List<User> getAllUsers();

}
