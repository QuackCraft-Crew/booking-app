package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.user.UserRegistrationRequestDto;
import com.example.accommodationbookingservice.dto.user.UserResponseDto;
import com.example.accommodationbookingservice.exception.RegistrationException;
import com.example.accommodationbookingservice.mapper.UserMapper;
import com.example.accommodationbookingservice.model.Role;
import com.example.accommodationbookingservice.model.RoleName;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.RoleRepository;
import com.example.accommodationbookingservice.repository.UserRepository;
import com.example.accommodationbookingservice.service.UserService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
                    throws RegistrationException {
        if (userRepository.existsUserByEmail(requestDto.email())) {
            throw new RegistrationException("User with email: "
                    + requestDto.email() + " already exists");
        }
        User user = userMapper.toUserModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(getUserRole());
        return userMapper.toUserDto(userRepository.save(user));
    }

    private Set<Role> getUserRole() {
        return new HashSet<>(Collections.singletonList(
                roleRepository.findRoleByName(RoleName.CUSTOMER)
        ));
    }
}
