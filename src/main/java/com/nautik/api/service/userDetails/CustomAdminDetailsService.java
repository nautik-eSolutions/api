package com.nautik.api.service.userDetails;

import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.repository.user.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;


    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByUsername(userName).orElseThrow(()-> new EntityNotFoundException("No administrator was found "));


        return new CustomAdminUserDetails(admin);
    }
}
