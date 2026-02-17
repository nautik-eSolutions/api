package com.nautik.api.controller.users;


import com.nautik.api.domain.Token;
import com.nautik.api.domain.users.LoginEmailRequest;
import com.nautik.api.domain.users.LoginRequest;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.dto.user.UserLoginResponse;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody LoginRequest login){

        return ResponseEntity.ok(userService.login(login));
    }
    @PostMapping("/login/email")
    public ResponseEntity<UserLoginResponse> loginEmail(@RequestBody LoginEmailRequest login){

        return ResponseEntity.ok(userService.loginEmail(login));
    }

    @PostMapping("/register")
    public ResponseEntity<UserLoginResponse> registerUser(@RequestBody UserDto userInfo){
        return ResponseEntity.ok(userService.createUser(userInfo));
    }
}
