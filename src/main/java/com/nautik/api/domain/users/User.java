package com.nautik.api.domain.users;


import com.nautik.api.domain.Boat;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.dto.user.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
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
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;


    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Boat> boats;


    /*
    @OneToOne(mappedBy = "user")
    private Admin admin;

*/

}
