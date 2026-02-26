package com.nautik.api.dto.port.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto  {
    private Integer id;
    private String name;
    private String vat;
    private String email;
    private String phone;
    private Integer adminId;
}