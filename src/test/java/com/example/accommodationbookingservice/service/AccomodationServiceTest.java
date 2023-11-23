package com.example.accommodationbookingservice.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.accommodationbookingservice.dto.accommodation.AccommodationDto;
import com.example.accommodationbookingservice.exception.EntityNotFoundException;
import com.example.accommodationbookingservice.mapper.AccommodationMapper;
import com.example.accommodationbookingservice.mapper.AccommodationMapperImpl;
import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Address;
import com.example.accommodationbookingservice.repository.AccommodationRepository;
import com.example.accommodationbookingservice.repository.AddressRepository;
import com.example.accommodationbookingservice.service.impl.AccommodationServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class AccomodationServiceTest {
    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private AddressRepository addressRepository;

    @Spy
    private AccommodationMapper accommodationMapper = new AccommodationMapperImpl();

    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    @Test
    @DisplayName(value = "Get all accommodations")
    void getAllAccommodation_Valid_ReturnList() {
        Accommodation accommodation = getDefaultAccommodation();
        Accommodation anotherAccommodation = getDefaultAccommodation();
        anotherAccommodation.setId(2L);
        List<Accommodation> accommodationList = List.of(accommodation,
                anotherAccommodation);
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<Accommodation> accommodationPage = new PageImpl<>(
                accommodationList, pageable, accommodationList.size());
        when(accommodationRepository.findAll(pageable)).thenReturn(accommodationPage);

        List<AccommodationDto> actualDtoList = accommodationService.findAll(pageable);

        Assertions.assertEquals(2, actualDtoList.size());

    }

    @Test
    @DisplayName(value = "Get error by  unexisted id")
    void testFindById_WhenAccommodationNotExists() {
        when(accommodationRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                    () -> accommodationService.findById(1L));
        Assertions.assertEquals("There is not accommodations in db by id 1",
                exception.getMessage());
        verify(accommodationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenAccommodationExists() {
        Accommodation accommodation = getDefaultAccommodation();
        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        AccommodationDto accommodationDto = accommodationService.findById(1L);

        Assertions.assertEquals(accommodation.getId(), accommodationDto.getId());
        Assertions.assertEquals(accommodation.getType(), accommodationDto.getType());

        verify(accommodationRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById_WhenAccommodationExists() {
        Long accommodationIdToDelete = 1L;
        accommodationService.deleteById(accommodationIdToDelete);
        verify(accommodationRepository).deleteById(accommodationIdToDelete);
    }

    private Accommodation getDefaultAccommodation() {
        Accommodation accommodation = new Accommodation();
        accommodation.setAmenities("some");
        accommodation.setType(Accommodation.Type.HOUSE);
        accommodation.setSize("size");
        accommodation.setId(1L);
        accommodation.setAddress(getDefaultAddress());
        return accommodation;
    }

    private Address getDefaultAddress() {
        Address address = new Address();
        address.setCountry("Ukraine");
        address.setId(1L);
        address.setStreetNumber("123");
        address.setStreetName("Uliza 1");
        return address;
    }
}
