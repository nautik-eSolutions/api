package com.nautik.api.repository.user;

import com.nautik.api.domain.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    public Optional<Admin> findByUser_UserName(String userName);

    Optional<Admin> findByUser_Id(Integer userId);

    Optional<Admin> findAdminByUser_Id(Integer userId);
}
