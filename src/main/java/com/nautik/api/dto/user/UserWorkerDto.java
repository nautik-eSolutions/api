package com.nautik.api.dto.user;

import lombok.Data;

import java.util.Date;
@Data
public class UserWorkerDto {

    private String email;
    private String password;
    private String roleName;
    private String userName;
    private String firstName;
    private String lastName;
    private String identificationDocument;
    private Date birthDate;
    private Integer portId;
}
