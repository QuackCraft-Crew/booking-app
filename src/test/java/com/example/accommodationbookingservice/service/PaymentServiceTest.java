package com.example.accommodationbookingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.mapper.PaymentMapper;
import com.example.accommodationbookingservice.mapper.impl.PaymentMapperImpl;
import com.example.accommodationbookingservice.model.Payment;
import com.example.accommodationbookingservice.repository.PaymentRepository;
import com.example.accommodationbookingservice.service.impl.PaymentServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    @Spy
    private PaymentMapper paymentMapper = new PaymentMapperImpl();
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    @DisplayName(value = "Get payments by valid user id")
    void getPaymentsByUserId_ValidUserId_ReturnList() {
        Payment p1 = getDefaultPayment();
        p1.setId(1L);
        Payment p2 = getDefaultPayment();
        p2.setId(2L);
        List<Payment> payments = List.of(p1, p2);

        Long userId = 1L;
        when(paymentRepository.getPaymentsByUserId(userId)).thenReturn(payments);
        List<PaymentResponseDto> actual = paymentService.getPaymentsByUserId(userId);
        List<PaymentResponseDto> expected = payments.stream()
                .map(paymentMapper::toPaymentDto)
                .toList();
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    private Payment getDefaultPayment() {
        Payment payment = new Payment();
        payment.setStatus(Payment.Status.PAID);
        payment.setSessionId("sessionId");
        payment.setSessionUrl("sessionUrl");
        payment.setAmountToPay(BigDecimal.valueOf(100));
        return payment;
    }
}
