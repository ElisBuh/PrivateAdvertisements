package com.privateadvertisements.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.privateadvertisements.util.LocalDateDeserializer;
import com.privateadvertisements.util.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserNewDto {

    private String login;
    private String passwords;

}
