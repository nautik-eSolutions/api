package com.nautik.api.service.userDetails;

import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.User;
import com.nautik.api.repository.user.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;


    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("No administrator was found "));


        return new org.springframework.security.core.userdetails.User(
                String.valueOf(admin.getId()),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(admin.getRole().getName()))
        );
    }
}
