package com.nautik.api.repository.roles;

import com.nautik.api.domain.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

   public List<Role> findByDescription(String description);
}
