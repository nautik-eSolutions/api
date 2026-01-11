package com.nautik.api.controller.users;

import com.nautik.api.dto.user.AdminDto;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.service.users.AdminService;
import com.nautik.api.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/administrators")
public class AdminController {

    @Autowired
    private final AdminService adminService;


    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getUserById (@PathVariable Long id){
        return ResponseEntity.ok(adminService.findAdminById(id));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<AdminDto> createUser(@PathVariable Integer userId) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(
               adminService.createAdmin(userId)
       );
    }

    @PatchMapping
    public ResponseEntity<AdminDto> updateUser(@PathVariable Integer userId) {

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(
                adminService.updateAdmin(userId)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestBody Long id){
        adminService.deleteAdminById(id);
        return ResponseEntity.ok().build();
    }


}
