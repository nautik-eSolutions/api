package com.nautik.api.dto.admin;

import com.nautik.api.domain.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {
    private Integer id;
    private String username;
    private Integer companyId;
    private Integer portId;
    private String adminType;
    private Token token;// "COMPANY" o "PORT"
}