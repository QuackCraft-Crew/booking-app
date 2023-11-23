package com.example.accommodationbookingservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.accommodationbookingservice.dto.user.RequestUserRoleDto;
import com.example.accommodationbookingservice.dto.user.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    protected static MockMvc mockMvc;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Find book by existent id")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {"classpath:database/all/delete-all.sql",
            "classpath:database/roles/insert-roles.sql",
            "classpath:database/users/insert-users.sql",
            "classpath:database/users-roles/insert-users-roles.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void setUserRole_ValidRequest_Success() throws Exception {
        Long userId = 1L;
        RequestUserRoleDto request = new RequestUserRoleDto("ADMIN");
        MvcResult result = mockMvc.perform(put("/users/" + userId + "/role")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDto.class);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("email", "test1@gmail.com")
                .hasFieldOrPropertyWithValue("firstName", "user1")
                .hasFieldOrPropertyWithValue("lastName", "surname");
    }

    @Test
    @DisplayName("Get user valid authentication")
    @WithMockUser(username = "test1@gmail.com")
    @Sql(scripts = {"classpath:database/all/delete-all.sql",
            "classpath:database/roles/insert-roles.sql",
            "classpath:database/users/insert-users.sql",
            "classpath:database/users-roles/insert-users-roles.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getUser_ValidRequest_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDto.class);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("email", "test1@gmail.com")
                .hasFieldOrPropertyWithValue("firstName", "user1")
                .hasFieldOrPropertyWithValue("lastName", "surname");
    }
}
