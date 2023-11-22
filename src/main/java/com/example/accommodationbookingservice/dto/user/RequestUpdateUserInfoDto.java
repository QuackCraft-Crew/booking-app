package com.example.accommodationbookingservice.dto.user;

import jakarta.validation.constraints.Email;

public record RequestUpdateUserInfoDto(@Email String email,
                                       String firstName,
                                       String lastName,
                                       String password,
                                       String newPassword) {
}
