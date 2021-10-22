package com.privateadvertisements.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChatDto {

    private String name;

    private List<MessagesDto> messagesDtoList;

//    private List<UserDto> users;
}
