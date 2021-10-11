package com.privateadvertisements.model.dto;

import com.privateadvertisements.model.Messages;
import com.privateadvertisements.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
public class ChatDto {

    private String name;

    private List<MessagesDto> messagesDtoList;

//    private List<UserDto> users;
}
