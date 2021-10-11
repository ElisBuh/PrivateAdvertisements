package com.privateadvertisements.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.privateadvertisements.model.Category;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.Photograph;
import com.privateadvertisements.model.StatusAd;
import com.privateadvertisements.model.User;
import com.privateadvertisements.util.LocalDateDeserializer;
import com.privateadvertisements.util.LocalDateSerializer;
import com.privateadvertisements.util.LocalDateTimeDeserializer;
import com.privateadvertisements.util.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AdvertisementDto {
    private List<CommentDto> comments;
    private CategoryDto categoryDto;
    private String title;
    private String content;
    private BigDecimal cost;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime datePublication;

    private StatusAd statusAd;
    private List<PhotographDto> photographs;
}
