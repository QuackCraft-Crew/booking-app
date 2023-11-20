package com.example.accommodationbookingservice.annotation;

import com.example.accommodationbookingservice.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;

public class PasswordMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(@NotNull UserRegistrationRequestDto dto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return dto.password().equals(dto.repeatPassword());
    }
}
