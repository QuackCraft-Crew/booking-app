package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.user.RequestUpdateUserInfoDto;
import com.example.accommodationbookingservice.dto.user.RequestUserRoleDto;
import com.example.accommodationbookingservice.dto.user.UserLoginRequestDto;
import com.example.accommodationbookingservice.dto.user.UserRegistrationRequestDto;
import com.example.accommodationbookingservice.dto.user.UserResponseDto;
import com.example.accommodationbookingservice.exception.ChangePasswordException;
import com.example.accommodationbookingservice.exception.EntityNotFoundException;
import com.example.accommodationbookingservice.exception.RegistrationException;
import com.example.accommodationbookingservice.mapper.UserMapper;
import com.example.accommodationbookingservice.model.Role;
import com.example.accommodationbookingservice.model.RoleName;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.RoleRepository;
import com.example.accommodationbookingservice.repository.UserRepository;
import com.example.accommodationbookingservice.security.AuthenticationService;
import com.example.accommodationbookingservice.service.UserService;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsUserByEmail(requestDto.email())) {
            throw new RegistrationException("User with email: "
                    + requestDto.email() + " already exists");
        }
        User user = userMapper.toUserModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(setUserRole(RoleName.USER));
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto setNewRoleToUser(Long userId, RequestUserRoleDto roleName) {
        User user = isUserExist(userId);
        user.setRoles(setUserRole(
                RoleName.valueOf(roleName.roleName())));
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponseDto getInfo(Long userId) {
        return userMapper.toUserDto(userRepository.getReferenceById(userId));
    }

    @Override
    public UserResponseDto updateInfoAboutUser(Long userId, RequestUpdateUserInfoDto userInfoDto) {
        isValidChangePasswordFields(userInfoDto);
        User userToUpdate = isUserExist(userId);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Field[] fields = userInfoDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(userInfoDto);
                if (value != null && !String.valueOf(value).isEmpty()) {
                    Field userField = userToUpdate.getClass().getDeclaredField(field.getName());
                    if (field.getName().equals("password")) {
                        UserLoginRequestDto changePassword =
                                new UserLoginRequestDto(name, value.toString());
                        authenticationService.authenticateUser(changePassword);
                        userField.setAccessible(true);
                        userField.set(userToUpdate,
                                passwordEncoder.encode(userInfoDto.newPassword()));
                    } else {
                        userField.setAccessible(true);
                        userField.set(userToUpdate, value);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Something went wrong");
            } catch (NoSuchFieldException e) {
                continue;
            }
        }

        userToUpdate = userRepository.saveAndFlush(userToUpdate);

        return userMapper.toUserDto(userToUpdate);
    }

    private void isValidChangePasswordFields(RequestUpdateUserInfoDto userInfoDto) {
        if (userInfoDto.newPassword() != null && userInfoDto.password() == null) {
            throw new ChangePasswordException(
                    "You must input your present password if you want to change it");
        }
    }

    private User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User with id: " + userId
                        + " doesn`t exist"));
    }

    private Set<Role> setUserRole(RoleName roleName) {
        if (roleName.equals(RoleName.USER)) {
            return new HashSet<>(Collections.singletonList(
                    roleRepository.findRoleByName(RoleName.USER)
            ));
        }
        return new HashSet<>(Collections.singletonList(
                roleRepository.findRoleByName(RoleName.ADMIN)
        ));
    }
}
