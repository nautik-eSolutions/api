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

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public UserDto(User user){
        this.firstName= user.getFirstName();
        this.lastName= user.getLastName();
        this.email = user.getEmail();
    }

}
