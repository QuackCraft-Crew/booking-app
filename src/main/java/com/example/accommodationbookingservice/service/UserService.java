package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.user.RequestUpdateUserInfoDto;
import com.example.accommodationbookingservice.dto.user.RequestUserRoleDto;
import com.example.accommodationbookingservice.dto.user.UserRegistrationRequestDto;
import com.example.accommodationbookingservice.dto.user.UserResponseDto;
import com.example.accommodationbookingservice.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    UserResponseDto setNewRoleToUser(Long userId, RequestUserRoleDto roleName);

    UserResponseDto getInfo(Long userId);

    UserResponseDto updateInfoAboutUser(Long userId, RequestUpdateUserInfoDto userInfoDto);
}
