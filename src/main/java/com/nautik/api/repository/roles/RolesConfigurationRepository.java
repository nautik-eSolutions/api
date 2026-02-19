package com.nautik.api.repository.roles;

import com.nautik.api.domain.roles.RolesConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolesConfigurationRepository {
    
    Optional<RolesConfiguration> findByNameAndCompany_Name(String name, String companyName);
    List<RolesConfiguration> findByCompany_Name(String companyName);

    Optional<RolesConfiguration> findByIdAndCompany_Id(Long id, Long companyId);

    List<RolesConfiguration> findByCompany_Id(Long companyId);
}
