package com.nautik.api.dto.user;

import com.nautik.api.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserDto(User user){
        this.firstName= user.getFirstName();
        this.lastName= user.getLastName();
        this.email = user.getEmail();
    }

}
