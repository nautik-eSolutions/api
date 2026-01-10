package com.nautik.api.repository.user;

import com.nautik.api.domain.users.PortAdmin;
import com.nautik.api.domain.users.PortAdminId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortAdminRepository extends JpaRepository<PortAdmin, PortAdminId> {
}