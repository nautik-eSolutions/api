package com.nautik.api.service.userDetails;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class ComposedDetailsService implements UserDetailsService {

    private final CustomUserDetailsService customUserDetailsService;


    private final CustomAdminDetailsService adminDetailsService;

    private List<UserDetailsService> serviceList;

    @PostConstruct
    public void setServices(){
        List<UserDetailsService> newServices = new ArrayList<>();
        newServices.add(customUserDetailsService);
        newServices.add(adminDetailsService);
        this.serviceList = newServices;
    }

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (UserDetailsService service : serviceList){
            try {
                return service.loadUserByUsername(username);
            }catch (UsernameNotFoundException ex){
                continue;
            }
        }
        throw new UsernameNotFoundException("User Not Found");
    }
}
