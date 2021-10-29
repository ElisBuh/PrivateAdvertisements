package com.privateadvertisements.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDto {


    private String name;

    private List<MessagesDto> messagesDtoList;

    private Integer[] userId;
}
