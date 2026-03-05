package com.nautik.api.domain.users;


import com.nautik.api.domain.Boat;
import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.dto.user.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name" , length = 45)
    private String lastName;

    @Column(name = "identification_document")
    private String identificationDocument;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;


    @Column(name = "created_at")
    private Timestamp created_at;

    @OneToMany(mappedBy = "user")
    private List<Boat> boats;




    /*
    @OneToOne(mappedBy = "user")
    private Admin admin;

*/

    public User(String email, String username, String name){
        this.email =email;
        this.userName = username;
        this.firstName = name;
        this.lastName = name;
    }



}
