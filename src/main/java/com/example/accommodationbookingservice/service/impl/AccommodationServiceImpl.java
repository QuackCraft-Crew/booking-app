package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.mapper.AccommodationMapper;
import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.repository.AccommodationRepository;
import com.example.accommodationbookingservice.service.AccommodationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;

    @Override
    public List<AccommodationDto> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable).stream()
                .map(accommodationMapper::toDto)
                .toList();
    }

    @Override
    public AccommodationDto findById(Long id) {
        return accommodationMapper.toDto(accommodationRepository.findById(id).orElseThrow(() ->
                new RuntimeException("There is not accommodations in db by id %d"
                        .formatted(id))));
    }

    @Override
    public AccommodationDto save(AccommodationRequestDto requestDto) {
        return accommodationMapper.toDto(
                accommodationRepository.save(accommodationMapper.toModel(requestDto))
        );
    }

    @Override
    public AccommodationDto update(Long id, AccommodationRequestDto requestDto) {
        if (!accommodationRepository.existsById(id)) {
            throw new RuntimeException("There is not book in db by id %d"
                    .formatted(id));
        }
        Accommodation updatedAccommodation = (accommodationMapper.toModel(requestDto));
        updatedAccommodation.setId(id);
        return accommodationMapper.toDto(accommodationRepository.save(updatedAccommodation));
    }

    @Override
    public void deleteById(Long id) {
        accommodationRepository.deleteById(id);
    }
}
