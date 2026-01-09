package com.nautik.api.Controller;

<<<<<<< HEAD:src/main/java/com/nautik/api/Controllers/UserController.java
import com.nautik.api.repositories.UserRepository;
import org.springframework.stereotype.Controller;
=======
>>>>>>> main:src/main/java/com/nautik/api/Controller/UserController.java
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

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
