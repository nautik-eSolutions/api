package com.nautik.api.dto.port;

import com.nautik.api.dto.user.CompanyAdminDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto implements Serializable {
    private Integer id;
    private String name;
    private String vat;
    private String email;
    private String phone;
    private CompanyAdminDto admin;
}