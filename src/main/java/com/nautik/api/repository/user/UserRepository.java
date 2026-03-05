package com.nautik.api.repository.user;



import com.nautik.api.domain.Port;
import com.nautik.api.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   public Optional<User> findByFirstName(String firstName);

   Optional<User> findUserById(Integer id);

   public Optional<User> findByLastName(String lastName);

   public Optional<User> findByEmail(String email);

   public Optional<User> getByUserName(String userName);
   public void deleteById(Integer id);

    Optional<User> findByid(Integer id);

   Optional<User> findByUserName(String userName);

   Optional<User> findUserByUserName(String userName);

   Optional<User> findUserByEmail(String email);
}
