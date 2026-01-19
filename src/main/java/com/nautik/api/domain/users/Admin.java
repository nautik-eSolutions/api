package com.nautik.api.domain.users;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Admin (User user){
        this.user = user;
    }
}
