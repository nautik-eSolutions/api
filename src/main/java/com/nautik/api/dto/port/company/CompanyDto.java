package com.nautik.api.dto.port.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class CompanyDto  {
    private Integer id;
    private String name;
    private String vat;
    private String email;
    private String phone;
    private Integer adminId;
    private String namePrefix;

    public CompanyDto(Integer id, String name, String vat, String email, String phone, Integer adminId) {
        this.id = id;
        this.name = name;
        this.vat = vat;
        this.email = email;
        this.phone = phone;
        this.adminId = adminId;
        this.namePrefix = this.name.substring(0,4);
    }
}