package com.nautik.api.repository.user;

import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.AdminId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, AdminId> {
}