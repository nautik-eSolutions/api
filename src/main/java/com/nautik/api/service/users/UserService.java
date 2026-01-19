package com.nautik.api.service.users;

import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public UserDtoResponse findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        return modelMapper.map(user, UserDtoResponse.class);
    }

    public UserDtoResponse findUserByFirstName(String firstName){
        User user = userRepository.findByFirstName(firstName).orElseThrow();
        return modelMapper.map(user,UserDtoResponse.class);
    }


    public UserDtoResponse createUser(UserDto userDto){
        User providedUser = modelMapper.map(userDto,User.class);
        return modelMapper.map(
                userRepository.save(providedUser),
                UserDtoResponse.class);
    }

    public UserDtoResponse updateUser(UserDto userDto,String firstName){

        User searchedUser =userRepository.findByFirstName(firstName).orElseThrow();
        User providedUser = modelMapper.map(userDto,User.class);

        providedUser.setId(searchedUser.getId());

        return modelMapper.map(
                userRepository.save(providedUser),
                UserDtoResponse.class);
    }

    public void deleteUser(String firstName){
        User searchedUser = userRepository.findByFirstName(firstName).orElseThrow();
        userRepository.deleteById(searchedUser.getId());
    }



}