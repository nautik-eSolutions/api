package com.nautik.api.service.users;

import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserDto findUserById(Integer id) {
        return new UserDto(userRepository.findById(id).orElseThrow());
    }
    public UserDto createUser(UserDto userDto){
        User user = new User(userDto);
        return new UserDto(userRepository.save(user));
    }

    public UserDto updateUser(UserDto userDto){
        User user = new User(userDto);
        return new UserDto(userRepository.save(user));
    }
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }



}