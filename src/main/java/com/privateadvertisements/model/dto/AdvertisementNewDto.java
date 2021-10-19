package com.privateadvertisements.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AdvertisementNewDto {
    private String title;
    private String content;
    private BigDecimal cost;
}
