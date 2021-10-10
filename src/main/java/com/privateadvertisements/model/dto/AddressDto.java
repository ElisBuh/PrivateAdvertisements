package com.privateadvertisements.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {
    private CountryDto countryDto;
    private CityDto cityDto;
    private Integer postIndex;
    private String street;
    private Integer numHouse;
    private Integer numFlat;
}
