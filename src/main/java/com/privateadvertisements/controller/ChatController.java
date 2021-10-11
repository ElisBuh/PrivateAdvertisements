package com.privateadvertisements.controller;

import com.privateadvertisements.api.dao.IChatDao;
import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.dto.ChatDtoWithUser;
import com.privateadvertisements.util.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chats")
public class ChatController {

    private final Mapper mapper;
    private final IChatDao chatDao;

    public ChatController(Mapper mapper, IChatDao chatDao) {
        this.mapper = mapper;
        this.chatDao = chatDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDtoWithUser> read(@PathVariable(name = "id") Integer id) {
//        log.info("read");
//        User user = userDao.findById(id).get();
        Chat chat = chatDao.get(id);
//        UserDto userDto = mapper.convertUserToUserDto(user);
        ChatDtoWithUser chatDto = mapper.convertChatToChatDtoWithUser(chat);
        return new ResponseEntity<>(chatDto, HttpStatus.OK);
    }
}
