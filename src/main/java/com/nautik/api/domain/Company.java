package com.nautik.api.domain;

import com.nautik.api.domain.roles.RolesConfiguration;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "vat", nullable = false, length = 20)
    private String vat;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "namePrefix", length = 100)
    private String namePrefix;

    @Column(name = "phone", nullable = false, length = 100)
    private String phone;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;


    @OneToMany(mappedBy = "company")
    private List<Port> ports;

/*
    @OneToMany(mappedBy = "company")
    private List<RolesConfiguration>rolesConfigurations;

*/
}