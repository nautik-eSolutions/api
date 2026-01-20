package com.nautik.api.controller.users;

import com.nautik.api.dto.user.UserDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private final UserService userService;


    @GetMapping("/{firstName}")
    public ResponseEntity<UserDtoResponse> getUserByFirstName(@PathVariable String firstName) {
        return ResponseEntity.ok(userService.findUserByFirstName(firstName));
    }


    @PostMapping("/administrators")
    public ResponseEntity<UserDtoResponse> createAdminUser(@RequestBody UserDto user) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(userService.createAdminUser(user));
    }


    @PostMapping
    public ResponseEntity<UserDtoResponse> createUser(@RequestBody UserDto user) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(userService.createUser(user));
    }

    @PatchMapping("/{firstName}")
    public ResponseEntity<UserDtoResponse> updateUser(
            @RequestBody UserDto user,
            @PathVariable String firstName) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(userService.updateUser(user,firstName));
    }

    @DeleteMapping("/{firstName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String firstName) {
        userService.deleteUser(firstName);
        return ResponseEntity.ok().build();
    }


}
