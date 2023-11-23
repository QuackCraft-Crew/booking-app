package com.example.accommodationbookingservice.service;

import static org.mockito.Mockito.when;

import com.example.accommodationbookingservice.dto.user.UserResponseDto;
import com.example.accommodationbookingservice.mapper.UserMapper;
import com.example.accommodationbookingservice.mapper.impl.UserMapperImpl;
import com.example.accommodationbookingservice.model.RoleName;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.UserRepository;
import com.example.accommodationbookingservice.service.impl.UserServiceImpl;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName(value = "Get valid user fields by valid user id")
    void getUserByUserId_ValidUserId_ReturnUserInfoValid() {
        User defaultUser = getDefaultUser();
        String expectedName = "user1";
        when(userRepository.getReferenceById(1L)).thenReturn(defaultUser);
        UserResponseDto info = userService.getInfo(1L);
        Assertions.assertEquals(info.firstName(), expectedName);
    }

    private User getDefaultUser() {
        User user = new User();
        user.setRoles(new HashSet<>(RoleName.USER.ordinal()));
        user.setId(1L);
        user.setEmail("test1@gmail.com");
        user.setFirstName("user1");
        user.setLastName("surname");
        return user;
    }
}
