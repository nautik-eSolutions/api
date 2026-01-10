package com.nautik.api.repository;



import com.nautik.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   User findByFirstName(String firstName);
   User findByLastName(String lastName);
   User findByEmail(String email);
   User findByid(Integer id);

}
