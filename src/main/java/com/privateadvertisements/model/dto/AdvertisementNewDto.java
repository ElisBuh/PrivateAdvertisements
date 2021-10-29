package com.privateadvertisements.model.dto;

import com.privateadvertisements.model.StatusAd;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AdvertisementNewDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private BigDecimal cost;
    @NotBlank
    private String category;
    private StatusAd statusAd;
}
