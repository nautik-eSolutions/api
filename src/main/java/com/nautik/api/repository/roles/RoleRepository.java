package com.nautik.api.repository.roles;

import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.roles.RolesConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

   public List<Role> findByDescription(String description);
   Optional<Role> findByNameAndRolesConfiguration(String name, RolesConfiguration rolesConfiguration);
   public Role findByName(String name);
   Optional<Role> findByNameAndRolesConfiguration_NameAndRolesConfiguration_Company_Name(String name, String rolesConfigurationName, String rolesConfigurationCompanyName);
   
   List<Role> findRolesByRolesConfiguration_NameAndRolesConfiguration_Company_Name(String rolesConfigurationName, String rolesConfigurationCompanyName);
}
