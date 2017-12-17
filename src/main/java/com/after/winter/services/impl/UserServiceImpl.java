package com.after.winter.services.impl;

import com.after.winter.model.User;
import com.after.winter.repository.UserRepository;
import com.after.winter.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUserByEmail(String email) {
    if (email != null && !email.isEmpty()) {
      return userRepository.getUserByEmail(email);
    }
    return null;
  }

  @Override
  public User getUser(Long id) {
    if (id != null) {
      return userRepository.findOne(id);
    }
    return null;
  }

  @Override
  public User createUser(User user) {
    if (user != null) {
      return userRepository.saveAndFlush(user);
    }
    return null;
  }

  @Override
  public User updateUser(User user) {
    if (user != null && userRepository.exists(user.getId())) {
       return userRepository.saveAndFlush(user);
    }
    return null;
  }

  @Override
  public boolean deleteUser(Long userId) {
    if (userId != null && userRepository.exists(userId)) {
      userRepository.delete(userId);
      return true;
    }
    return false;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}

