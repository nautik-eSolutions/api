package com.nautik.api.domain;

import com.nautik.api.domain.moorings.MooringDimension;
import com.nautik.api.domain.moorings.PriceConfiguration;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.roles.RolesConfiguration;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "port")
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "port")
    private List<Admin> admins = new ArrayList<>();

    @OneToMany(mappedBy = "port")
    private List<MooringDimension> mooringDimensions =  new ArrayList<>();

    @OneToMany(mappedBy = "port")
    private List<MooringDimension> photos =  new ArrayList<>();

    @OneToMany(mappedBy = "port")
    private List<PriceConfiguration> priceConfigurations = new ArrayList<>();
    /*
    @ManyToOne
    @JoinColumn(name = "roles_configuration_id")
    private RolesConfiguration rolesConfiguration;
    */
}