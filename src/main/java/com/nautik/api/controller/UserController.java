package com.nautik.api.controller;

import com.nautik.api.domain.User;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/{firstName}")
    public User getUserByFirstName (@PathVariable String firstName){

        return new User();
    }
    @PostMapping("/create")
    public void createUser(
    @RequestBody String firstName,
    @RequestBody String lastName,
    @RequestBody String email,
    @RequestBody String password
    )
    {
       userService.saveUser(firstName,lastName,email,password);
    }
}
