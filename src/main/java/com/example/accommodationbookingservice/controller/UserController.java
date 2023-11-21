package com.example.accommodationbookingservice.controller;

import com.example.accommodationbookingservice.dto.user.RequestUpdateUserInfoDto;
import com.example.accommodationbookingservice.dto.user.RequestUserRoleDto;
import com.example.accommodationbookingservice.dto.user.UserResponseDto;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "User management",description = "Endpoints for saw user history of booking")
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    @Operation(summary = "Update role for user (only for ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserRole(@PathVariable @Positive Long id,
                                   @RequestBody RequestUserRoleDto roleName) {
        return userService.setNewRoleToUser(id, roleName);
    }

    @GetMapping("/me")
    @Operation(summary = "Show info about user account")
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto getUserRole(Authentication authentication) {
        return userService.getInfo(getUserId(authentication));
    }

    @PutMapping("/me")
    @Operation(summary = "Update info about user")
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto updateInfo(Authentication authentication,
                                      @RequestBody RequestUpdateUserInfoDto infoDto) {
        return userService.updateInfoAboutUser(getUserId(authentication), infoDto);
    }

    private Long getUserId(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }
}
