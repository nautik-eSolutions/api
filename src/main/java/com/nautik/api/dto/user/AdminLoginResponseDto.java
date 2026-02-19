package com.nautik.api.dto.user;

import com.nautik.api.domain.Token;
import lombok.Data;

@Data
public class AdminLoginResponseDto {
    String token;
    String role;

    public AdminLoginResponseDto(String token, String role){
        this.role=role;
        this.token=token;
    }
}
