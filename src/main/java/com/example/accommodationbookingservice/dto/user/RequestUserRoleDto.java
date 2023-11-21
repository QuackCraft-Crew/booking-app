package com.example.accommodationbookingservice.dto.user;

import jakarta.validation.constraints.NotBlank;

public record RequestUserRoleDto(@NotBlank String roleName) {
}
