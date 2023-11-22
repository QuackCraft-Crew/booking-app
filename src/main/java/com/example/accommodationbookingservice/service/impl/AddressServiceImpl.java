package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.address.AddressDto;
import com.example.accommodationbookingservice.dto.address.UpdateAddressRequestDto;
import com.example.accommodationbookingservice.mapper.AddressMapper;
import com.example.accommodationbookingservice.model.Address;
import com.example.accommodationbookingservice.repository.AddressRepository;
import com.example.accommodationbookingservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public AddressDto update(Long addressId, UpdateAddressRequestDto requestDto) {
        Address addressFromDB = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
        addressMapper.updateAddress(requestDto, addressFromDB);
        return addressMapper.toDto(addressFromDB);
    }
}
