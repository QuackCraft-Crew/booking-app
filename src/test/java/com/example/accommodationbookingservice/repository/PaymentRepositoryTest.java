package com.example.accommodationbookingservice.repository;

import com.example.accommodationbookingservice.model.Payment;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTest {
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @Sql(scripts = {"classpath:database/addresses/insert-address.sql",
            "classpath:database/accommodations/insert-accommodation.sql",
            "classpath:database/users/insert-users.sql",
            "classpath:database/bookings/insert-bookings.sql",
            "classpath:database/payments/insert-payments.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Get payments by valid user id")
    void getPaymentsByUserId_ValidUserId_ReturnList() {
        Long userId = 1L;
        List<Payment> actual = paymentRepository.getPaymentsByUserId(userId);
        Assertions.assertEquals(2, actual.size());
    }
}