package com.nautik.api.repository.user;

import com.nautik.api.domain.users.CompanyAdmin;
import com.nautik.api.domain.users.CompanyAdminId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, CompanyAdminId> {
}