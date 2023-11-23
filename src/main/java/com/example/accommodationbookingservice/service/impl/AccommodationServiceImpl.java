package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.dto.accommodation.AccommodationRequestDto;
import com.example.accommodationbookingservice.exception.EntityNotFoundException;
import com.example.accommodationbookingservice.mapper.AccommodationMapper;
import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Address;
import com.example.accommodationbookingservice.repository.AccommodationRepository;
import com.example.accommodationbookingservice.repository.AddressRepository;
import com.example.accommodationbookingservice.service.AccommodationService;
import com.example.accommodationbookingservice.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;
    private final AddressRepository addressRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public List<AccommodationDto> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable).stream()
                .map(accommodationMapper::toDto)
                .toList();
    }

    @Override
    public AccommodationDto findById(Long id) {
        return accommodationMapper.toDto(accommodationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is not accommodations in db by id %d"
                        .formatted(id))));
    }

    @Override
    public AccommodationDto save(AccommodationRequestDto requestDto) {
        Accommodation accommodation = accommodationMapper.toModel(requestDto);
        AccommodationDto savedAccommodation = accommodationMapper.toDto(
                accommodationRepository.save(accommodation));
        notificationService.sendReleasedAccommodationNotification(accommodation);
        return savedAccommodation;
    }

    @Override
    public AccommodationDto update(Long id, AccommodationRequestDto requestDto) {
        Accommodation existingAccommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Accommodation not found with id: " + id));
        Accommodation updatedAccommodation = (accommodationMapper.toModel(requestDto));
        updateModel(updatedAccommodation, existingAccommodation);
        return accommodationMapper.toDto(accommodationRepository.save(existingAccommodation));
    }

    @Override
    public void deleteById(Long id) {
        accommodationRepository.deleteById(id);
    }

    public void updateModel(Accommodation updated, Accommodation existed) {
        existed.setType(updated.getType());
        existed.setSize(updated.getSize());
        existed.setAmenities(updated.getAmenities());
        existed.setDailyRate(updated.getDailyRate());
        existed.setAvailability(updated.getAvailability());

        Address existedAddress = addressRepository.findById(existed.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Address not found with id: " + existed.getId()));

        if (updated.getAddress() != null) {
            updateAddress(updated.getAddress(), existedAddress);
        }
    }

    private void updateAddress(Address updated, Address existed) {
        existed.setStreetName(updated.getStreetName());
        existed.setStreetNumber(updated.getStreetNumber());
        existed.setCountry(updated.getCountry());
        addressRepository.save(existed);
    }
}
