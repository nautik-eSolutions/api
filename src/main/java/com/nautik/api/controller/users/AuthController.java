package com.nautik.api.controller.users;


import com.nautik.api.domain.Token;
import com.nautik.api.domain.users.LoginRequest;
import com.nautik.api.domain.users.User;
import com.nautik.api.service.jwt.JwtService;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequest login){

        return ResponseEntity.ok(userService.login(login));
    }
}
