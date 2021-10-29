package com.privateadvertisements.model.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AddressDto {
    @NotNull
    private CountryDto countryDto;
    @NotNull
    private CityDto cityDto;
    @NotBlank
    private Integer postIndex;
    @NotBlank
    private String street;
    @NotBlank
    private Integer numHouse;
    @NotBlank
    private Integer numFlat;
}
