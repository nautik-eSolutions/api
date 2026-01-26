package com.nautik.api.repository.roles;

import com.nautik.api.domain.roles.Capability;
import com.nautik.api.domain.roles.RolesConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapabilityRepository extends JpaRepository<Capability,Long> {

    Capability findByNameAndRolesConfiguration(String name, RolesConfiguration rolesConfiguration);

    List<Capability> findByNameAndRolesConfiguration_Name(String name, String rolesConfigurationName);

    List<Capability> findByRolesConfiguration(RolesConfiguration rolesConfiguration);
}
