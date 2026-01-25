package com.nautik.api.service.roles;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.roles.Capability;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.roles.RolesConfiguration;
import com.nautik.api.dto.roles.CapabilityDto;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.dto.roles.RoleResponseDto;
import com.nautik.api.dto.roles.RolesConfigurationDto;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.roles.CapabilityRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.repository.roles.RolesConfigurationRepository;
import com.sun.jdi.connect.spi.TransportService;
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
    private final CapabilityRepository capabilityRepository;
    private final PortRepository portRepository;

    private final ModelMapper modelMapper;

    public RolesConfigurationDto createRolesConfiguration(
            String companyName,
            RolesConfigurationDto rolesConfigurationDto) {

        RolesConfiguration providedRoleConfiguration = modelMapper.map(rolesConfigurationDto, RolesConfiguration.class);

        Company company = companyRepository.findByName(companyName).orElseThrow();


        providedRoleConfiguration.setCompany(company);

        return modelMapper.map(rolesConfigurationRepository.save(providedRoleConfiguration), RolesConfigurationDto.class);

    }


    public void deleteRolesConfiguration(String companyName, String configurationName) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository.
                findByNameAndCompany_Name(configurationName, companyName)
                .orElseThrow();

        rolesConfigurationRepository.delete(rolesConfiguration);

    }


    public List<RolesConfigurationDto> getAllCompanyConfigurations(String companyName) {
        List<RolesConfiguration> rolesConfigurations = rolesConfigurationRepository.findByCompany_Name(companyName);


        return rolesConfigurations.
                stream().
                map(conf -> modelMapper.map
                        (conf, RolesConfigurationDto.class))
                .collect(Collectors.toList());
    }


    public RoleResponseDto createRole(
            String companyName,
            String configurationName,
            RoleCreateDto roleCreateDto
    ) {

        Role roleToCreate = modelMapper.map(roleCreateDto, Role.class);

        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByNameAndCompany_Name(configurationName, companyName)
                .orElseThrow();

        roleToCreate.setRolesConfiguration(rolesConfiguration);

        return modelMapper.map(roleRepository.save(roleToCreate), RoleResponseDto.class);

    }


    public List<RoleResponseDto> getAllRolesByConfigurationName(String companyName, String roleConfigurationName) {
        List<Role> roles = roleRepository
                .findRolesByRolesConfiguration_NameAndRolesConfiguration_Company_Name(roleConfigurationName, companyName
                );

        return roles.stream().map(role -> modelMapper.map(role, RoleResponseDto.class)).collect(Collectors.toList());

    }


    public void deleteRole(String configurationName, String company, String roleName) {
        Role roleToDelete = roleRepository
                .findByNameAndRolesConfiguration_NameAndRolesConfiguration_Company_Name(roleName, configurationName, company)
                .orElseThrow();

        roleRepository.delete(roleToDelete);
    }


    public RoleResponseDto updateRole(
            String companyName,
            String configurationName,
            RoleCreateDto roleCreateDto
    ) {

        Role roleToUpdate = modelMapper.map(roleCreateDto, Role.class);

        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByNameAndCompany_Name(configurationName, companyName)
                .orElseThrow();

        Role roleToExtractIdFrom = roleRepository
                .findByNameAndRolesConfiguration(roleCreateDto.getName(), rolesConfiguration)
                .orElseThrow();


        roleToUpdate.setId(roleToExtractIdFrom.getId());


        roleToUpdate.setRolesConfiguration(rolesConfiguration);

        return modelMapper.map(roleRepository.save(roleToUpdate), RoleResponseDto.class);

    }


    public CapabilityDto createCapability(String companyName, String configurationName, CapabilityDto capabilityDto) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByNameAndCompany_Name(configurationName, companyName).orElseThrow();

        Capability capability = modelMapper.map(capabilityDto, Capability.class);

        capability.setRolesConfiguration(rolesConfiguration);

        return modelMapper
                .map(capabilityRepository.save(capability), CapabilityDto.class);
    }


    public CapabilityDto updateCapability(String companyName, String configurationName, CapabilityDto capabilityDto) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByNameAndCompany_Name(configurationName, companyName).orElseThrow();

        Capability searchedCapability = capabilityRepository.findByNameAndRolesConfiguration(configurationName, rolesConfiguration);

        Capability providedCapability = modelMapper.map(capabilityDto, Capability.class);

        providedCapability.setId(searchedCapability.getId());

        return modelMapper
                .map(capabilityRepository.save(providedCapability), CapabilityDto.class);
    }


    public List<CapabilityDto> getAllCapabilities(String companyName, String configurationName) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByNameAndCompany_Name(configurationName, companyName).orElseThrow();

        List<Capability> capabilities = capabilityRepository.findByRolesConfiguration(rolesConfiguration);

        return capabilities
                .stream()
                .map(cap->modelMapper.map(cap, CapabilityDto.class))
                .collect(Collectors.toList());

    }


    public CapabilityDto getCapability(String companyName, String configurationName, String capabiltyName){
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByNameAndCompany_Name(configurationName, companyName)
                .orElseThrow();

        Capability capability = capabilityRepository
                .findByNameAndRolesConfiguration(capabiltyName,rolesConfiguration);

        return modelMapper.map(capability, CapabilityDto.class);

    }


}
