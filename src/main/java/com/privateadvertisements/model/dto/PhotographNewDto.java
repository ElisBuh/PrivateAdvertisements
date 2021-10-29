package com.privateadvertisements.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
public class PhotographNewDto {

    @URL
    private String[] paths;
}
