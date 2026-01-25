package com.nautik.api.service.roles;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.roles.RolesConfiguration;
import com.nautik.api.dto.roles.RolesConfigurationDto;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.repository.roles.RolesConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolesService {


    private final RoleRepository roleRepository;
    private final RolesConfigurationRepository rolesConfigurationRepository;
    private final CompanyRepository companyRepository;
    private final PortRepository portRepository;

    private final ModelMapper modelMapper;

    public RolesConfigurationDto createConfiguration(
            String companyName,
            RolesConfigurationDto rolesConfigurationDto) {

        RolesConfiguration providedRoleConfiguration = modelMapper.map(rolesConfigurationDto, RolesConfiguration.class);

        Company company =  companyRepository.findByName(companyName).orElseThrow();


        providedRoleConfiguration.setCompany(company);

        return modelMapper.map(rolesConfigurationRepository.save(providedRoleConfiguration), RolesConfigurationDto.class);


    }


}
