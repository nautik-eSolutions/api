package com.nautik.api.dto.user;

import com.nautik.api.domain.Token;
import lombok.Data;

@Data
public class AdminLoginResponseDto {
    String token;
    String role;
    Integer portId;
    public AdminLoginResponseDto(String token, String role, Integer portId) {
        this.token = token;
        this.role = role;
        this.portId = portId;
    }
}
