package com.nautik.api.controller;

import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/api")
    public User getUserByFirstName (){

        return new User();
    }

    @PostMapping("/create")
    public User createUser(
    @RequestBody UserDto user

    )
    {
       return userService.saveUser(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword());
    }


}
