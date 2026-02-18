package com.nautik.api.dto.user;

import com.nautik.api.domain.Company;
import lombok.Data;

import java.util.Date;

@Data
public class UserAdminDto {

    private String email;
    private String password;
    private String roleName;
    private String userName;
    private String firstName;
    private String lastName;
    private String identificationDocument;
    private Date birthDate;
    private Integer companyToId;



}
