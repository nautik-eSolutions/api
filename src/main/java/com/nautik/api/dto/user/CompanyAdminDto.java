package com.nautik.api.dto.user;

import com.nautik.api.domain.users.CompanyAdminId;
import lombok.Value;

import java.io.Serializable;

@Value
public class CompanyAdminDto implements Serializable {
    CompanyAdminId id;
    AdminDto admin;
}