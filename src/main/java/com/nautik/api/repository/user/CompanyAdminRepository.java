package com.nautik.api.repository.user;

import com.nautik.api.domain.users.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Long> {
}