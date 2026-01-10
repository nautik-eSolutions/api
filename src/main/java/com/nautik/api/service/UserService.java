package com.nautik.api.service;

import com.nautik.api.domain.User;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserDto getUserByFirstName(String firstName) {
        return new UserDto(userRepository.findByFirstName(firstName).orElseThrow());
    }
    public void saveUser(String firstName, String lastName, String email,String password){
        User user = new User(firstName,lastName,email,password);
        userRepository.save(user);
    }


}