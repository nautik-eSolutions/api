package com.nautik.api.service.roles;

import com.nautik.api.configuration.ModelMapperConfiguration;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.dto.roles.RoleCreateDto;
import com.nautik.api.dto.roles.RoleResponseDto;
import com.nautik.api.repository.roles.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    private RoleRepository repository;


    @Autowired
    private ModelMapperConfiguration mapper;

    public RoleResponseDto getRole(String name) {
        Role role = repository.findByName(name);
        return mapper.modelMapper().map(role, RoleResponseDto.class);
    }

    public RoleResponseDto saveRole(RoleCreateDto roleDto) {
        Role role = mapper.modelMapper().map(roleDto, Role.class);
        return mapper.modelMapper().map(repository.save(role), RoleResponseDto.class);
    }









}
