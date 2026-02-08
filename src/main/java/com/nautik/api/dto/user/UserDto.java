package com.nautik.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nautik.api.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;


    @Override
    public String toString() {
        return userName;
    }
}
