package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.address.AddressDto;
import com.example.accommodationbookingservice.dto.address.UpdateAddressRequestDto;

public interface AddressService {
    AddressDto update(Long addressId, UpdateAddressRequestDto requestDto);
}
