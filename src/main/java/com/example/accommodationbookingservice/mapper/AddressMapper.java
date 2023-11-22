package com.example.accommodationbookingservice.mapper;

import com.example.accommodationbookingservice.config.MapperConfig;
import com.example.accommodationbookingservice.dto.address.AddressDto;
import com.example.accommodationbookingservice.dto.address.UpdateAddressRequestDto;
import com.example.accommodationbookingservice.model.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddress(UpdateAddressRequestDto requestDto, @MappingTarget Address address);

    AddressDto toDto(Address address);
}
