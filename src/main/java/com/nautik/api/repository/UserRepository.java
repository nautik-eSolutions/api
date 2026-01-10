package com.nautik.api.repository;

import com.nautik.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

   public User findByFirstName(String firstName);
}
