package com.privateadvertisements.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.CreditCard;
import com.privateadvertisements.model.Messages;
import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.Role;
import com.privateadvertisements.util.LocalDateDeserializer;
import com.privateadvertisements.util.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
