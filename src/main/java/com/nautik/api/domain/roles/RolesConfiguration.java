package com.nautik.api.domain.roles;


import com.nautik.api.domain.Port;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="role_configuration")
public class RolesConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToMany(mappedBy = "rolesConfiguration")
    private List<Port> port;


    @OneToMany(mappedBy = "rolesConfiguration")
    private List<Role> roles;


}
