package com.nautik.api.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
public class UserController {
    @GetMapping
    public String test (){
        return "hola";
    }
}
