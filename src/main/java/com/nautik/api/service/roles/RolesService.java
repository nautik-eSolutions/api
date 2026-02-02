package com.nautik.api.service.roles;

import com.nautik.api.domain.Company;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;

import com.nautik.api.domain.roles.Capability;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.roles.RolesConfiguration;
import com.nautik.api.dto.roles.CapabilityDto;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.dto.roles.RoleResponseDto;
import com.nautik.api.dto.roles.RolesConfigurationDto;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.roles.CapabilityRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.repository.roles.RolesConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolesService {


    private final RoleRepository roleRepository;
    private final RolesConfigurationRepository rolesConfigurationRepository;
    private final CompanyRepository companyRepository;
    private final CapabilityRepository capabilityRepository;

    private final ModelMapper modelMapper;

    public RolesConfigurationDto createRolesConfiguration(
            Long companyId,
            RolesConfigurationDto rolesConfigurationDto) {

        RolesConfiguration providedRoleConfiguration = modelMapper.map(rolesConfigurationDto, RolesConfiguration.class);


        Company company = companyRepository.findById(companyId).orElseThrow(()->new ResourceNotFoundException("Company not found"));



        providedRoleConfiguration.setCompany(company);

        return modelMapper.map(rolesConfigurationRepository.save(providedRoleConfiguration), RolesConfigurationDto.class);

    }



    public void deleteRolesConfiguration(Long companyId, Long configurationId) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository.findByIdAndCompany_Id(configurationId, companyId).orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        rolesConfigurationRepository.delete(rolesConfiguration);

    }


    public List<RolesConfigurationDto> getAllCompanyConfigurations(Long companyId) {
        List<RolesConfiguration> rolesConfigurations = rolesConfigurationRepository.findByCompany_Id(companyId);
        return rolesConfigurations.
                stream().
                map(conf -> modelMapper.map
                        (conf, RolesConfigurationDto.class))
                .collect(Collectors.toList());
    }


    public RoleResponseDto createRole(
            Long companyId,
            Long configurationId,
            RoleCreateDto roleCreateDto
    ) {

        Role roleToCreate = modelMapper.map(roleCreateDto, Role.class);

        RolesConfiguration rolesConfiguration = rolesConfigurationRepository

                .findByIdAndCompany_Id(configurationId, companyId)
                .orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        roleToCreate.setRolesConfiguration(rolesConfiguration);

        return modelMapper.map(roleRepository.save(roleToCreate), RoleResponseDto.class);

    }


    public List<RoleResponseDto> getAllRolesByConfigurationName(String companyName, String roleConfigurationName) {
        List<Role> roles = roleRepository
                .findRolesByRolesConfiguration_NameAndRolesConfiguration_Company_Name(roleConfigurationName, companyName
                );

        return roles.stream().map(role -> modelMapper.map(role, RoleResponseDto.class)).collect(Collectors.toList());

    }

    public List<RoleResponseDto> getAllRolesByConfigurationId(Long companyId, Long roleConfigurationId) {
        List<Role> roles = roleRepository
                .findRolesByRolesConfiguration_IdAndRolesConfiguration_Company_Id(roleConfigurationId, companyId);

        return roles.stream().map(role -> modelMapper.map(role, RoleResponseDto.class)).collect(Collectors.toList());

    }


    public void deleteRole(Long configurationId, Long companyId, Long roleId) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByIdAndCompany_Id(configurationId, companyId).orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));


        System.out.println(companyId);
        System.out.println(configurationId);
        System.out.println(roleId);
        Role roleToDelete = roleRepository.findByIdAndRolesConfiguration(roleId,rolesConfiguration).orElseThrow(()->new ResourceNotFoundException("Role not found"));


        roleRepository.delete(roleToDelete);
    }


    public RoleResponseDto updateRole(
            Long companyId,
            Long configurationId,
            RoleCreateDto roleCreateDto
    ) {

        Role roleToUpdate = modelMapper.map(roleCreateDto, Role.class);

        RolesConfiguration rolesConfiguration = rolesConfigurationRepository

                .findByIdAndCompany_Id(configurationId, companyId)
                .orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        Role roleToExtractIdFrom = roleRepository
                .findByNameAndRolesConfiguration(roleCreateDto.getName(), rolesConfiguration)
                .orElseThrow(()->new ResourceNotFoundException("Role not found"));


        roleToUpdate.setId(roleToExtractIdFrom.getId());


        roleToUpdate.setRolesConfiguration(rolesConfiguration);

        return modelMapper.map(roleRepository.save(roleToUpdate), RoleResponseDto.class);

    }


    public CapabilityDto createCapability(Long  companyId, Long configurationId, CapabilityDto capabilityDto) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByIdAndCompany_Id(configurationId, companyId).orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        Capability capability = modelMapper.map(capabilityDto, Capability.class);

        capability.setRolesConfiguration(rolesConfiguration);

        return modelMapper
                .map(capabilityRepository.save(capability), CapabilityDto.class);
    }


    public CapabilityDto updateCapability(Long companyId, Long configurationId, CapabilityDto capabilityDto) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository

                .findByIdAndCompany_Id(configurationId, companyId).orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        Capability searchedCapability = capabilityRepository.findByIdAndRolesConfiguration(configurationId, rolesConfiguration);

        Capability providedCapability = modelMapper.map(capabilityDto, Capability.class);

        providedCapability.setId(searchedCapability.getId());

        return modelMapper
                .map(capabilityRepository.save(providedCapability), CapabilityDto.class);
    }


    public List<CapabilityDto> getAllCapabilities(Long companyId, Long configurationId) {
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByIdAndCompany_Id(configurationId, companyId).orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        List<Capability> capabilities = capabilityRepository.findByRolesConfiguration(rolesConfiguration);

        return capabilities
                .stream()
                .map(cap->modelMapper.map(cap, CapabilityDto.class))
                .collect(Collectors.toList());

    }


    public CapabilityDto getCapability(Long companyId, Long configurationId, Long capabiltyId){
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository

                .findByIdAndCompany_Id(configurationId, companyId)
                .orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));


        Capability capability = capabilityRepository
                .findByIdAndRolesConfiguration(capabiltyId,rolesConfiguration);

        return modelMapper.map(capability, CapabilityDto.class);

    }


    public List<CapabilityDto> assignCapabilityToRole(
            Long companyId,
            Long configurationId,
            Long roleId,
            Long capabilityId){
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository

                .findByIdAndCompany_Id(configurationId, companyId)
                .orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        Role role = roleRepository.findByIdAndRolesConfiguration(roleId,rolesConfiguration).orElseThrow(()->new ResourceNotFoundException("Role not found"));

        Capability capability = capabilityRepository
                .findByIdAndRolesConfiguration(capabilityId,rolesConfiguration);

        role.getCapabilities().add(capability);

        Role savedRole = roleRepository.save(role);
        return savedRole.getCapabilities().stream().map(cap->modelMapper.map(cap, CapabilityDto.class)).toList();
    }


    public List<CapabilityDto> removeCapabilityToRole(
            Long companyId,
            Long configurationId,
            Long roleId,
            Long capabilityId){
        RolesConfiguration rolesConfiguration = rolesConfigurationRepository
                .findByIdAndCompany_Id(configurationId, companyId)
                .orElseThrow(()->new ResourceNotFoundException("Roles configuration not found"));

        Role role = roleRepository.findByIdAndRolesConfiguration(roleId,rolesConfiguration).orElseThrow(()->new ResourceNotFoundException("Role not found"));


        Capability capability = capabilityRepository.findByIdAndRolesConfiguration(capabilityId,rolesConfiguration);
        role.getCapabilities().remove(capability);

        Role savedRole = roleRepository.save(role);
        return savedRole.getCapabilities().stream().map(cap->modelMapper.map(cap, CapabilityDto.class)).toList();
    }




}
