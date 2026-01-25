package com.nautik.api.domain.roles;

import com.nautik.api.domain.Company;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Capability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "capabilities")
    private List<Role> roles =  new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "roles_configuration_id")
    private RolesConfiguration rolesConfiguration;
}
