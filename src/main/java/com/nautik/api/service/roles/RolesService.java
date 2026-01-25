package com.nautik.api.service.roles;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.roles.RolesConfiguration;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.dto.roles.RoleResponseDto;
import com.nautik.api.dto.roles.RolesConfigurationDto;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.repository.roles.RolesConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolesService {


    private final RoleRepository roleRepository;
    private final RolesConfigurationRepository rolesConfigurationRepository;
    private final CompanyRepository companyRepository;
    private final PortRepository portRepository;

    private final ModelMapper modelMapper;

    public RolesConfigurationDto createRolesConfiguration(
            String companyName,
            RolesConfigurationDto rolesConfigurationDto) {

        RolesConfiguration providedRoleConfiguration = modelMapper.map(rolesConfigurationDto, RolesConfiguration.class);

        Company company =  companyRepository.findByName(companyName).orElseThrow();


        providedRoleConfiguration.setCompany(company);

        return modelMapper.map(rolesConfigurationRepository.save(providedRoleConfiguration), RolesConfigurationDto.class);

    }








    public void deleteRolesConfiguration(String companyName, String configurationName){
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository.
                findByNameAndCompany_Name(configurationName,companyName)
                .orElseThrow();

        rolesConfigurationRepository.delete(rolesConfiguration);

    }









    public List<RolesConfigurationDto> getAllCompanyConfigurations(String companyName){
        List<RolesConfiguration> rolesConfigurations = rolesConfigurationRepository.findByCompany_Name(companyName);


        return rolesConfigurations.
                stream().
                map(conf-> modelMapper.map
                (conf, RolesConfigurationDto.class))
                .collect(Collectors.toList());
    }







    public RoleResponseDto createRole(
            String companyName,
            String configurationName,
            RoleCreateDto roleCreateDto
    ){

        Role roleToCreate =modelMapper.map(roleCreateDto, Role.class);

        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByNameAndCompany_Name(configurationName,companyName)
                .orElseThrow();

        roleToCreate.setRolesConfiguration(rolesConfiguration);

        return modelMapper.map(roleRepository.save(roleToCreate),RoleResponseDto.class);

    }






}
