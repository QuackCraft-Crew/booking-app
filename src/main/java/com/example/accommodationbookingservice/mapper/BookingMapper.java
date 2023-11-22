package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.dto.booking.BookUpdateDto;
import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.model.Booking;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface BookingMapper {
    @Mapping(target = "accommodationId", source = "accommodation.id")
    @Mapping(target = "userId", source = "user.id")
    BookingDto toBookingDto(Booking booking);

    @Mapping(target = "accommodation.id", source = "accommodationId")
    @Mapping(target = "checkIn", source = "checkInDate")
    @Mapping(target = "checkOut", source = "checkOutDate")
    Booking toBookingModel(BookingRequestDto requestDto);

    @Mapping(target = "checkIn", source = "checkInDate")
    @Mapping(target = "checkOut", source = "checkOutDate")
    void updateBooking(BookUpdateDto dto, @MappingTarget Booking booking);
}
