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
public class UserDto {

    private Integer id;
    private Set<RoleDto> roles;
    private String login;
    private String passwords;
    private Integer rating;
    private Boolean enabled;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateRegistered;

    private AddressDto addressDto;
    private List<CreditCardDto> creditCardsDto;
    //    private List<Messages> messages;
    private PersonalUserInfoDto personalUserInfoDto;
    private Set<ChatDto> chats;
    private List<CommentDto> commentDtoList;
    private List<AdvertisementDto> advertisementDtoList;
}
