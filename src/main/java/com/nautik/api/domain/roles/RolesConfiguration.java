package com.nautik.api.domain.roles;


import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data

@Table(name="role_configuration")
public class RolesConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToMany(mappedBy = "rolesConfiguration")
    private List<Port> port;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false)
    private Company company;

    @OneToMany(mappedBy = "rolesConfiguration")
    private List<Role> roles;


    @OneToMany(mappedBy = "rolesConfiguration")
    private List<Capability> capabilities;


}
