package com.nautik.api.service.users;

import com.nautik.api.domain.Token;
import com.nautik.api.domain.exceptions.ResourceNotFoundException;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.LoginEmailRequest;
import com.nautik.api.domain.users.LoginRequest;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.user.AdminLoginResponseDto;
import com.nautik.api.dto.user.UserDto;
import com.nautik.api.dto.user.UserDtoResponse;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.port.PortRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.dto.user.UserLoginResponse;

import com.nautik.api.repository.user.AdminRepository;
import com.nautik.api.repository.user.UserRepository;
import com.nautik.api.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final PortRepository portRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public UserLoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            User user = userRepository.findByUserName(loginRequest.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            Token token =  jwtService.generateToken(user);


           return new UserLoginResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserName(),token );



        }

        throw new RuntimeException("Credenciales inválidas");
    }


    public AdminLoginResponseDto adminLogin(LoginRequest loginRequest) {
        Token token;
        User user;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            user = userRepository.findByUserName(loginRequest.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            token = jwtService.generateToken(user);
        }else{
            throw new RuntimeException("Credenciales inválidas");
        }

        Integer portId = null;
        if (user.getPort() != null) {
            portId = user.getPort().getId();
        }

        return new AdminLoginResponseDto(token.getToken(), user.getRole().getName(), portId);
    }

    public Token OAuthLogin(GoogleIdTokenInfo googleIdTokenInfo){

        String email = googleIdTokenInfo.getEmail();
        String name  = googleIdTokenInfo.getName();

        if (userRepository.findByEmail(email).isEmpty()){
           return jwtService.generateToken(userRepository.save(new User(email, email,name)));
        }

        return jwtService.generateToken(userRepository.findUserByEmail(email).orElseThrow());
    }




    public UserLoginResponse loginEmail(LoginEmailRequest loginRequest) {

        String userName = userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow().getUserName();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            Token token =  jwtService.generateToken(user);


            return new UserLoginResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserName(),token );
        }

        throw new RuntimeException("Credenciales inválidas");
    }





        public UserDtoResponse findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDtoResponse.class);
    }

    public UserDtoResponse findUserByFirstName(String firstName) {
        User user = userRepository.findByFirstName(firstName).orElseThrow(()->new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDtoResponse.class);
    }


    public UserLoginResponse createUser(UserDto userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User providedUser = userRepository.save(modelMapper.map(userDto, User.class));



        Token token =  jwtService.generateToken(providedUser);

        UserLoginResponse response =  new UserLoginResponse();
        response.setEmail(providedUser.getEmail());
        response.setFirstName(providedUser.getFirstName());
        response.setLastName(providedUser.getLastName());
        response.setUserName(providedUser.getUserName());
        response.setToken(token);

        return response;


    }







    public UserDtoResponse updateUser(UserDto userDto, Long userId) {

        User searchedUser = userRepository.findByid(Math.toIntExact(userId)).orElseThrow(()->new ResourceNotFoundException("User not found"));
        User providedUser = modelMapper.map(userDto, User.class);

        providedUser.setId(searchedUser.getId());

        return modelMapper.map(
                userRepository.save(providedUser),
                UserDtoResponse.class);
    }



    public void deleteUser(Long userid) {
        User searchedUser = userRepository.findByid(Math.toIntExact(userid)).orElseThrow(() -> new ResourceNotFoundException("User not found"));

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

    public List<UserDtoResponse> getAllUsers(){
        return userRepository.findAll().stream().map((element) -> modelMapper.map(element, UserDtoResponse.class)).toList();
    }


}