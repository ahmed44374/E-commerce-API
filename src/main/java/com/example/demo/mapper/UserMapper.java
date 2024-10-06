package com.example.demo.mapper;

import com.example.demo.dto.RegisterDto;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserMapper {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserMapper(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterDto toRegisterDTO(AppUser user) {
        RegisterDto registerDto= new RegisterDto();
        registerDto.setFirstName(user.getFirstName());
        registerDto.setLastName(user.getLastName());
        registerDto.setEmail(user.getEmail());
        registerDto.setPassword(user.getPassword());
        return registerDto;
    }
    public AppUser RegisterDtoToAppUser(RegisterDto registerDto) {
        AppUser appUser = new AppUser();
        appUser.setEmail(registerDto.getEmail());
        appUser.setFirstName(registerDto.getFirstName());
        appUser.setLastName(registerDto.getLastName());
        appUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            // Handle the case where the role does not exist
            throw new IllegalArgumentException("Role 'USER' not found");
        }
        appUser.setRoles(Collections.singletonList(roleRepository.findByName("USER")));

        return appUser;
    }
}

