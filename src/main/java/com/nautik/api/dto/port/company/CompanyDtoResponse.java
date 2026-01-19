package com.nautik.api.dto.port.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDtoResponse {
    private String name;
    private String vat;
    private String email;
    private String phone;
}
