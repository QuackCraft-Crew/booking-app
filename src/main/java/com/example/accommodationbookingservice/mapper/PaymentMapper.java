package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.config.MapperConfig;
import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface PaymentMapper {
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "amount", source = "amountToPay")
    PaymentResponseDto toPaymentDto(Payment payment);
}
