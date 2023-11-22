package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.dto.accommodation.UpdateAccommodationRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccommodationService {
    List<AccommodationDto> findAll(Pageable pageable);

    AccommodationDto findById(Long id);

    AccommodationDto save(AccommodationRequestDto requestDto);

    AccommodationDto update(Long id, UpdateAccommodationRequestDto createDto);

    void deleteById(Long id);
}
