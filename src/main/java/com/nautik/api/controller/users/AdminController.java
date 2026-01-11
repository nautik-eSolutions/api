package com.nautik.api.controller.users;

import com.nautik.api.dto.user.UserDto;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AdminController {

    @Autowired
    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById (@PathVariable Integer id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(
               userService.createUser(user)
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
