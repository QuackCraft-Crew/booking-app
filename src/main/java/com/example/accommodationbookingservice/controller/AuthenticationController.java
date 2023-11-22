package com.example.accommodationbookingservice.controller;

import com.example.accommodationbookingservice.dto.user.UserLoginRequestDto;
import com.example.accommodationbookingservice.dto.user.UserLoginResponseDto;
import com.example.accommodationbookingservice.dto.user.UserRegistrationRequestDto;
import com.example.accommodationbookingservice.dto.user.UserResponseDto;
import com.example.accommodationbookingservice.exception.RegistrationException;
import com.example.accommodationbookingservice.security.AuthenticationService;
import com.example.accommodationbookingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Registration/Login",description = "Endpoints for registration or login")
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    @Operation(summary = "Login user into system")
    public UserResponseDto register(@RequestBody @Valid
                                UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Register new user to DB")
    public UserLoginResponseDto login(@RequestBody @Valid
                                          UserLoginRequestDto requestDto) {
        return authenticationService.authenticateUser(requestDto);
    }
}
