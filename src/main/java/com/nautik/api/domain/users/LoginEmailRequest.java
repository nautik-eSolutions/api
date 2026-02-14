package com.nautik.api.domain.users;

import lombok.Data;
import lombok.Getter;

@Data

public class LoginEmailRequest {

    private String email;
    private String password;

}
