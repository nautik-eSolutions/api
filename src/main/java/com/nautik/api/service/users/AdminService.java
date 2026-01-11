package com.nautik.api.service.users;

import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.AdminDto;
import com.nautik.api.repository.user.AdminRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final UserRepository userRepository;

    public AdminDto findAdminById(Long id){
        return new AdminDto(adminRepository.findById(id).orElseThrow());
    }

    public void deleteAdminById(Long id){

    }

    public AdminDto updateAdmin(AdminDto adminDto){

        User user = userRepository.findById(Math.toIntExact(adminDto.getUserId())).orElseThrow();

        return new AdminDto(adminRepository.save(new Admin(user)));
    }
    public AdminDto CreateAdmin(AdminDto adminDto){

        User user = userRepository.findById(Math.toIntExact(adminDto.getUserId())).orElseThrow();

        return new AdminDto(adminRepository.save(new Admin(user)));
    }



}
