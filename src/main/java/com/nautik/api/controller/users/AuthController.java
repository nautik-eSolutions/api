package com.nautik.api.controller.users;


import com.nautik.api.domain.users.User;
import com.nautik.api.service.jwt.JwtService;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<String> login(String userName, String password){
        return null;
    }
}
