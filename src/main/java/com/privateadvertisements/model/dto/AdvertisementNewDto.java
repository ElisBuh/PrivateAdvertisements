package com.privateadvertisements.model.dto;

import com.privateadvertisements.model.StatusAd;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AdvertisementNewDto {
    private String title;
    private String content;
    private BigDecimal cost;
    private String category;
    private StatusAd statusAd;
}
