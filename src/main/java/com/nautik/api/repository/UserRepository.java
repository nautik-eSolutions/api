package com.nautik.api.repository;



import com.nautik.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   public Optional<User> findByFirstName(String firstName);
   public User findByLastName(String lastName);
   public User findByEmail(String email);
   public User findByid(Integer id);

}
