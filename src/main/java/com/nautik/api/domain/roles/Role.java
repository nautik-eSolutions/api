package com.nautik.api.domain.roles;

import com.nautik.api.domain.users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "role_capability",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "capability_id")
    )
    private List<Capability> capabilities = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    List<User>users;

    @ManyToOne
    @JoinColumn(name = "roles_configuration")
    private RolesConfiguration rolesConfiguration;



}
