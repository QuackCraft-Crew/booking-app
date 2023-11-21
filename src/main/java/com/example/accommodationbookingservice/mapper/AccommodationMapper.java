package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.config.MapperConfig;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.model.Accommodation;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {

    AccommodationDto toDto(Accommodation accommodation);

    Accommodation toModel(AccommodationRequestDto requestDto);

}
