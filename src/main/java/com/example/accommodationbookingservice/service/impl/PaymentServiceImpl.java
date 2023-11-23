package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.exception.EntityNotFoundException;
import com.example.accommodationbookingservice.exception.PaymentException;
import com.example.accommodationbookingservice.mapper.PaymentMapper;
import com.example.accommodationbookingservice.model.Booking;
import com.example.accommodationbookingservice.model.Payment;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.PaymentRepository;
import com.example.accommodationbookingservice.service.NotificationService;
import com.example.accommodationbookingservice.service.PaymentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Override
    @Transactional
    public void successPayment(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find payment by sessionId"));
        Booking booking = payment.getBooking();
        booking.setStatus(Booking.Status.CONFIRMED);
        payment.setStatus(Payment.Status.PAID);
        notificationService.sendSuccessfulPaymentMessage();
    }

    @Override
    @Transactional
    public void cancelPayment(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find payment by sessionId"));
        Booking booking = payment.getBooking();
        booking.setStatus(Booking.Status.CANCELED);
        payment.setStatus(Payment.Status.CANCELED);
        notificationService.sendFailedPaymentMessage();
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByUserId(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (userId == user.getId()) {
            return paymentRepository.getPaymentsByUserId(userId).stream()
                    .map(paymentMapper::toPaymentDto).toList();
        }
        throw new PaymentException("Something wrong");
    }
}
