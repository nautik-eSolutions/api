package com.nautik.api.dto.user;

import com.nautik.api.domain.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private Token token;
}


