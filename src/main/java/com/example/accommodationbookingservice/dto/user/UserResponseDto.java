package com.example.accommodationbookingservice.dto.user;

public record UserResponseDto(Long id,
                              String email,
                              String firstName,
                              String lastName) {
}
