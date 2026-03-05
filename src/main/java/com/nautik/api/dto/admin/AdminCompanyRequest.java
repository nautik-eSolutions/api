package com.nautik.api.dto.admin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCompanyRequest {
    @NotNull
    @Max(45)
    private String username;
    @NotNull
    @Max(20)
    private String password;
    @NotNull
    private Integer companyId;
}
