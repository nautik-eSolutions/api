package com.nautik.api.controller.users;


import com.nautik.api.domain.Token;
import com.nautik.api.domain.users.LoginEmailRequest;
import com.nautik.api.domain.users.LoginRequest;
import com.nautik.api.dto.user.AdminLoginResponseDto;
import com.nautik.api.service.users.GoogleIdTokenInfo;
import com.nautik.api.service.users.GoogleOauthService;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final GoogleOauthService googleOauthService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequest login){

        return ResponseEntity.ok(userService.login(login));
    }

    @PostMapping("/login/administrator")
    public ResponseEntity<AdminLoginResponseDto> loginAdmin(@RequestBody LoginRequest login){
        return ResponseEntity.ok(userService.adminLogin(login));
    }

    @PostMapping("login/oauth")
    public ResponseEntity<Token> loginOauth( @RequestHeader("Google-Token") String authHeader){
        GoogleIdTokenInfo googleIdTokenInfo = googleOauthService.verifyIdToken(authHeader);

        return ResponseEntity.ok(userService.OAuthLogin(googleIdTokenInfo));
    }

    @PostMapping("/login/email")
    public ResponseEntity<Token> loginEmail(@RequestBody LoginEmailRequest login){

        return ResponseEntity.ok(userService.loginEmail(login));
    }
}
