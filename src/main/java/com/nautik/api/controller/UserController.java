package com.nautik.api.controller;

import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById (@PathVariable Long id){
        return ResponseEntity.ok(new User());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(
               userService.saveUser(user)
       );
    }

    @PatchMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(
                userService.updateUser(user)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestBody Integer id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


}
