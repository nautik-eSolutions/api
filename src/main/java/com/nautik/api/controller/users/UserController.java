package com.nautik.api.controller.users;

import com.nautik.api.dto.user.UserAdminDto;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> getAllusers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDtoResponse> getUserByFirstName(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserById(Math.toIntExact(userId)));
    }


    @PostMapping("/administrators")
    public ResponseEntity<UserDtoResponse> createAdminUser(@RequestBody UserDto user) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(userService.createAdminUser(user));
    }


    @PostMapping
    public ResponseEntity<UserDtoResponse> createUser(@RequestBody UserDto user) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(userService.createUser(user));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDtoResponse> updateUser(
            @RequestBody UserDto user,
            @PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(userService.updateUser(user,userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }



    //----------------------------------------------------------------------------------



    @PostMapping("/company/{companyId}/administrator")
    public ResponseEntity<UserDtoResponse> createAdministrator(@RequestBody UserAdminDto adminDto, @PathVariable Integer companyId){


        return ResponseEntity.ok(userService.createCompanyAdministrator(adminDto, companyId));
    }


}
