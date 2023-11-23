package com.example.accommodationbookingservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Get user bookings by valid user id")
    void getFalse_invalidEmail_AssertError() {
        boolean actual = userRepository.existsUserByEmail("invalidMail@gmail.com");
        Assertions.assertFalse(actual);
    }

    @Test
    @Sql(scripts = {"classpath:database/all/delete-all.sql",
            "classpath:database/users/insert-users.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Get user bookings by valid user id")
    void getTrue_validEmail_AssertTrue() {
        boolean actual = userRepository.existsUserByEmail("test1@gmail.com");
        Assertions.assertTrue(actual);
    }
}
