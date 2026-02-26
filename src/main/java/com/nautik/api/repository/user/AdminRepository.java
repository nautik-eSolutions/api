package com.nautik.api.repository.user;

import com.nautik.api.domain.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {


    Optional<Admin> findByUsername(String username);
}
