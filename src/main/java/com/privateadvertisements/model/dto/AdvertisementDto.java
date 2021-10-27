package com.privateadvertisements.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.privateadvertisements.model.StatusAd;
import com.privateadvertisements.util.LocalDateDeserializer;
import com.privateadvertisements.util.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class AdvertisementDto {
    private String title;
    private String content;
    private BigDecimal cost;
    private List<CommentDto> comments;
    private CategoryDto categoryDto;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate datePublication;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate datePublicationOff;

    private StatusAd statusAd;
    private List<PhotographDto> photographs;
}
