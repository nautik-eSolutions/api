package com.nautik.api.service.users;

import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.repository.user.AdminRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;


    public UserDtoResponse findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        return modelMapper.map(user, UserDtoResponse.class);
    }

    public UserDtoResponse findUserByFirstName(String firstName) {
        User user = userRepository.findByFirstName(firstName).orElseThrow();
        return modelMapper.map(user, UserDtoResponse.class);
    }


    public UserDtoResponse createUser(UserDto userDto) {
        User providedUser = modelMapper.map(userDto, User.class);
        return modelMapper.map(
                userRepository.save(providedUser),
                UserDtoResponse.class);
    }

    public UserDtoResponse updateUser(UserDto userDto, Long userId) {

        User searchedUser = userRepository.findByid(Math.toIntExact(userId)).orElseThrow();
        User providedUser = modelMapper.map(userDto, User.class);

        providedUser.setId(searchedUser.getId());

        return modelMapper.map(
                userRepository.save(providedUser),
                UserDtoResponse.class);
    }

    public void deleteUser(Long userid) {
        User searchedUser = userRepository.findByid(Math.toIntExact(userid)).orElseThrow();
        userRepository.deleteById(searchedUser.getId());
    }



    public UserDtoResponse createAdminUser(UserDto userDto){
        User providedUser = modelMapper.map(userDto, User.class);
        User createdUser   = userRepository.save(providedUser);
        adminRepository.save(new Admin(createdUser));
        return modelMapper.map(
                userRepository.save(providedUser),
                UserDtoResponse.class);

    }


}