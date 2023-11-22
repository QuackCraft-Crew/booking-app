package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.config.MapperConfig;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.util.MapUtil;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {
    MapUtil mapUtil = new MapUtil();

    AccommodationDto toDto(Accommodation accommodation);

    @Mapping(source = "address.streetName", target = "address.streetName")
    @Mapping(source = "address.streetNumber", target = "address.streetNumber")
    @Mapping(source = "address.country", target = "address.country")
    @Mapping(source = "address.city", target = "address.city")
    Accommodation toModel(AccommodationRequestDto requestDto);

    @AfterMapping
    default void mapAddressFields(AccommodationRequestDto requestDto,
                                  @MappingTarget Accommodation accommodation) {
        if (requestDto != null && requestDto.address() != null) {
            accommodation.getAddress().setStreetName(requestDto.address().getStreetName());
            accommodation.getAddress().setStreetNumber(requestDto.address().getStreetNumber());
            accommodation.getAddress().setCountry(requestDto.address().getCountry());
            accommodation.getAddress().setCity(requestDto.address().getCity());
        }
    }

    @AfterMapping
    default void mapsUrl(@MappingTarget AccommodationDto accommodationDto,
                         Accommodation accommodation) {
        accommodationDto.setMapUrl(mapUtil.addressToUrl(
                accommodation.getAddress().getStreetName(),
                accommodation.getAddress().getStreetNumber(),
                accommodation.getAddress().getCity(),
                accommodation.getAddress().getCountry()));

    }
}
