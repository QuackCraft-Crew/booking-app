package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.config.MapperConfig;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.model.Accommodation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {

    AccommodationDto toDto(Accommodation accommodation);

    @Mapping(source = "address.streetName", target = "address.streetName")
    @Mapping(source = "address.streetNumber", target = "address.streetNumber")
    @Mapping(source = "address.country", target = "address.country")
    Accommodation toModel(AccommodationRequestDto requestDto);

    @AfterMapping
    default void mapAddressFields(AccommodationRequestDto requestDto,
                                  @MappingTarget Accommodation accommodation) {
        if (requestDto != null && requestDto.address() != null) {
            accommodation.getAddress().setStreetName(requestDto.address().getStreetName());
            accommodation.getAddress().setStreetNumber(requestDto.address().getStreetNumber());
            accommodation.getAddress().setCountry(requestDto.address().getCountry());
        }
    }
}
