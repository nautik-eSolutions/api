package com.nautik.api.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
@RequestMapping(produces = "application/json")
public class UserController {
    @GetMapping
    public String test (){
        return "";
    }
}
