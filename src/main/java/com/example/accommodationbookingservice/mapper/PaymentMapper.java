package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.config.MapperConfig;
import com.example.accommodationbookingservice.dto.payment.PaymentDto;
import com.example.accommodationbookingservice.model.Payment;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface PaymentMapper {
    PaymentDto toPaymentDto(Payment payment);
}
