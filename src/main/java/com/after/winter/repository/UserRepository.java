package com.after.winter.repository;

import com.after.winter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


  User getUserByEmail(String email);

}
