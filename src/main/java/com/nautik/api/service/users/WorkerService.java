package com.nautik.api.service.users;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.exceptions.ForbiddenToResourceException;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.UserAdminDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final PortRepository portRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    /*
    public void authorizeAdminToPortResource(Integer userId, Integer portId){
        User administrator =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("No user was found"));
        administrator.getCompany().getPorts().stream()
                .filter(port1 -> Objects.equals(port1.getId(), portId)).findFirst().orElseThrow(() -> new ForbiddenToResourceException("You are not the administrator to this port"));
    }





    /*
    public List<Admin> getWorkersByPort(Integer portId){
        Port port = portRepository.findById(portId).orElseThrow(()->new ResourceNotFoundException("Port not found"));
        List<Admin> admins = port.getAdmins();

        if (workers.isEmpty()){
            throw new ResourceNotFoundException("This port has no workers");
        }

        return workers.stream().map(worker-> modelMapper.map(worker, UserDtoResponse.class)).toList();
    }
*/


    public UserDtoResponse createCompanyAdministrator(UserAdminDto userAdminDto, Integer companyId){

        Company company = companyRepository.findById(companyId).orElseThrow(()->new ResourceNotFoundException("No company found "));
        Role role = roleRepository.findByName(userAdminDto.getRoleName());
        User providedUser = modelMapper.map(userAdminDto, User.class);

        userAdminDto.setPassword(passwordEncoder.encode(userAdminDto.getPassword()));
        providedUser.setRole(role);

        User savedUser =  userRepository.save(providedUser);

        companyRepository.save(company);


        return modelMapper.map(savedUser, UserDtoResponse.class);

    }
}
