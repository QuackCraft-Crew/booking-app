package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.config.MapperConfig;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.dto.accommodation.UpdateAccommodationRequestDto;
import com.example.accommodationbookingservice.model.Accommodation;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {
    @Mapping(source = "address.id", target = "addressId")
    AccommodationDto toDto(Accommodation accommodation);

    @Mapping(source = "address.streetName", target = "address.streetName")
    @Mapping(source = "address.streetNumber", target = "address.streetNumber")
    @Mapping(source = "address.country", target = "address.country")
    Accommodation toModel(AccommodationRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccommodation(UpdateAccommodationRequestDto requestDto,
                                      @MappingTarget Accommodation accommodation);

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
