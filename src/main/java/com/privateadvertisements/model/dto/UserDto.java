package com.privateadvertisements.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.privateadvertisements.util.LocalDateTimeDeserializer;
import com.privateadvertisements.util.LocalDateTimeSerializer;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
public class UserDto {

    @Email
    @NotBlank
    private String login;

    @NotNull
    private Integer id;


    private Set<RoleDto> roles;
    private Integer rating;
    private Boolean enabled;
    @JsonIgnore
    @Size(min = 8, max = 20)
    @NotBlank
    private String passwords;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateRegistered;

    private AddressDto addressDto;
    private List<CreditCardDto> creditCardsDto;
    private PersonalUserInfoDto personalUserInfoDto;
    @JsonIgnore
    private Set<ChatDto> chats;
    @JsonIgnore
    private List<CommentDto> commentDtoList;
    private List<AdvertisementDto> advertisementDtoList;
}
