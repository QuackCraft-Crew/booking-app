package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.CreateAccommodationDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccommodationService {
    List<AccommodationDto> findAll(Pageable pageable);

    AccommodationDto getById(Long id);

    AccommodationDto save(CreateAccommodationDto requestDto);

    AccommodationDto update(Long id, CreateAccommodationDto createDto);

    void deleteById(Long id);
}
