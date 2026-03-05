package com.nautik.api.service.userDetails;

import com.nautik.api.domain.users.Admin;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class CustomAdminUserDetails implements UserDetails {

    private final Integer adminId;
    private final String username;
    private final String password;
    private final String role;
    private final Integer companyId;
    private final Integer portId;
    private final boolean isCompanyAdmin;

    public CustomAdminUserDetails(Admin admin) {
        this.adminId = admin.getId();
        this.username = String.valueOf(admin.getId());
        this.password = admin.getPassword();
        this.role = admin.getRole().getName();

        if (admin.getCompany() != null) {
            this.isCompanyAdmin = true;
            this.companyId = admin.getCompany().getId();
            this.portId = null;
        } else if (admin.getPort() != null) {
            this.isCompanyAdmin = false;
            this.companyId = admin.getPort().getCompany().getId();
            this.portId = admin.getPort().getId();
        } else {
            this.isCompanyAdmin = false;
            this.companyId = null;
            this.portId = null;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}