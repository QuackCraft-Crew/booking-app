package com.example.accommodationbookingservice.dto.user;

import com.example.accommodationbookingservice.annotation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(fields = {"password", "repeatPassword"})
public record UserRegistrationRequestDto(@NotBlank @Email String email,
                                         @NotBlank @Size(min = 4,max = 15) String password,
                                         @NotBlank @Size(min = 4,max = 15) String repeatPassword,
                                         @NotBlank String firstName,
                                         @NotBlank String lastName) {
}
