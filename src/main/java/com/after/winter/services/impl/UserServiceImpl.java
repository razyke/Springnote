package com.after.winter.services.impl;

import com.after.winter.aop.LogTimeOfExecute;
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
  @LogTimeOfExecute
  public User getUserByEmail(String email) {
    if (email != null && !email.isEmpty()) {
      return userRepository.getUserByEmail(email);
    }
    return null;
  }

  @Override
  @LogTimeOfExecute
  public User getUser(Long id) {
    if (id != null) {
      return userRepository.findById(id).get();
    }
    return null;
  }

  @Override
  @LogTimeOfExecute
  public User createUser(User user) {
    if (user != null) {
      return userRepository.saveAndFlush(user);
    }
    return null;
  }

  @Override
  @LogTimeOfExecute
  public User updateUser(User user) {
    if (user != null && userRepository.existsById(user.getId())) {
       return userRepository.saveAndFlush(user);
    }
    return null;
  }

  @Override
  @LogTimeOfExecute
  public boolean deleteUser(Long userId) {
    if (userId != null && userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
      return true;
    }
    return false;
  }

  @Override
  @LogTimeOfExecute
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}

