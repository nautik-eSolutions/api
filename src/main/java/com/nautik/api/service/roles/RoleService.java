package com.nautik.api.service.roles;

import com.nautik.api.configuration.ModelMapperConfiguration;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.dto.roles.RoleDto;
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

    public RoleDto getRole(String name) {
        Role role = repository.findByName(name);
        return mapper.modelMapper().map(role, RoleDto.class);
    }

    public RoleDto saveRole(RoleDto roleDto) {
        Role role = mapper.modelMapper().map(roleDto, Role.class);
        return mapper.modelMapper().map(repository.save(role), RoleDto.class);
    }


}
