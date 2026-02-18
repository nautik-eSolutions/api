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
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "vat", nullable = false, length = 20)
    private String vat;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 100)
    private String phone;

    @OneToMany(mappedBy = "company")
    private List<Admin> admins;
    @OneToOne()
    @JoinColumn(name = "user_id")
    private User administrator;


/*
    @OneToMany(mappedBy = "company")
    private List<RolesConfiguration>rolesConfigurations;

*/
}