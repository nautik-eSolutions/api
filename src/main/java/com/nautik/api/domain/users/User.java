package com.nautik.api.domain.users;


import com.nautik.api.domain.roles.Role;
import com.nautik.api.dto.user.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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
    /*
    @OneToOne(mappedBy = "user")
    private Admin admin;

*/

    public User(String firstName, String lastName, String email, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
    }
    public User(UserDto userDto){
        this.firstName=userDto.getFirstName();
        this.lastName= userDto.getLastName();
        this.email= userDto.getEmail();
        this.password= userDto.getPassword();
    }

}
