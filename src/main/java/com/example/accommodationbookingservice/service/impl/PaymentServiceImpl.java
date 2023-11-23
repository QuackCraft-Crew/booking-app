package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.exception.EntityNotFoundException;
import com.example.accommodationbookingservice.mapper.PaymentMapper;
import com.example.accommodationbookingservice.model.Booking;
import com.example.accommodationbookingservice.model.Payment;
import com.example.accommodationbookingservice.repository.PaymentRepository;
import com.example.accommodationbookingservice.service.NotificationService;
import com.example.accommodationbookingservice.service.PaymentService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ExecutorService executorService;

    public PaymentServiceImpl() {
    }

    @Override
    @Transactional
    public void successPayment(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find payment by sessionId"));
        Booking booking = payment.getBooking();
        booking.setStatus(Booking.Status.CONFIRMED);
        payment.setStatus(Payment.Status.PAID);
        executorService.execute(
                () -> notificationService.sendSuccessfulPaymentMessage()

        );
    }

    @Override
    @Transactional
    public void cancelPayment(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find payment by sessionId"));
        Booking booking = payment.getBooking();
        booking.setStatus(Booking.Status.CANCELED);
        payment.setStatus(Payment.Status.CANCELED);
        executorService.execute(
                () -> notificationService.sendFailedPaymentMessage()
        );
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByUserId(Long userId) {
        return paymentRepository.getPaymentsByUserId(userId).stream()
                .map(paymentMapper::toPaymentDto).toList();
    }
}
