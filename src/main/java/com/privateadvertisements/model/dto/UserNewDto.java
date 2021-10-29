package com.privateadvertisements.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserNewDto {


    @Email
    @NotBlank
    private String login;

    @Size(min = 8, max = 20)
    @NotBlank
    private String passwords;

}
