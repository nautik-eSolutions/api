package com.nautik.api.repository.user;



import com.nautik.api.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   public Optional<User> findByFirstName(String firstName);

   public Optional<User> findByLastName(String lastName);

   public Optional<User> findByEmail(String email);

   public Optional<User> findByid(Integer id);

   public void deleteById(Integer id);

}
