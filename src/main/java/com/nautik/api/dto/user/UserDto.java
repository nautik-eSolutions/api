package com.nautik.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nautik.api.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
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
