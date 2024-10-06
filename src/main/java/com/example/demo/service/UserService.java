package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.RegisterDto;
import com.example.demo.entity.AppUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }
    public boolean userExist(String email) {
        Optional<AppUser> appUser = userRepository.findByEmail(email);
        return appUser.isPresent();
    }

    public RegisterDto toRegisterDTO(AppUser user) {
        return userMapper.toRegisterDTO(user);
    }

    public AppUser RegisterDtoToAppUser(RegisterDto registerDto) {
        return userMapper.RegisterDtoToAppUser(registerDto);
    }

    public ResponseEntity<String> saveUser(RegisterDto registerDto) {
        if(userExist(registerDto.getEmail())) {
            return new ResponseEntity<>("Username is Taken!", HttpStatus.BAD_REQUEST);
        }
        AppUser createdUser = RegisterDtoToAppUser(registerDto);
        userRepository.save(createdUser);
        return new ResponseEntity<>("User created successfully",HttpStatus.CREATED);
    }

    public ResponseEntity<String> login(@RequestBody RegisterDto registerDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerDto.getEmail(),registerDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(registerDto);
        return new ResponseEntity<>(token,HttpStatus.ACCEPTED);
    }
    public AppUser getCurrentLoggedInUser(String token) {
        String username = jwtService.extractUsername(token);
        return userRepository.findByEmail(username).orElseThrow();
    }
    public boolean isAdmin(){
        Collection<?> isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (Object auth : isAdmin) {
            String role = auth.toString();
            if(role.equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }

}
