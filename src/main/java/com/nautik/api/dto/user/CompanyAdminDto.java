package com.nautik.api.dto.user;

import lombok.Value;

import java.io.Serializable;

@Value
public class CompanyAdminDto implements Serializable {
    Long id;
    AdminDto admin;
}