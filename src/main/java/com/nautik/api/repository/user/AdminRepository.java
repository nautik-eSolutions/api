package com.nautik.api.repository.user;

import com.nautik.api.domain.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Optional<Admin> findById(Long id);

    public void deleteById(Long id);
}