package com.privateadvertisements.controller;

import com.privateadvertisements.api.service.IChatService;
import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.dto.ChatDto;
import com.privateadvertisements.model.dto.MessagesDto;
import com.privateadvertisements.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/chats")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final Mapper mapper;
    private final IChatService chatService;

    public ChatController(Mapper mapper, IChatService chatService) {
        this.mapper = mapper;
        this.chatService = chatService;
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ChatDto chatDto) {
        log.info("create chat between: {}", chatDto);
        chatService.creat(chatDto.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> read(@PathVariable(name = "id") Integer id) {
        log.info("Read chat: {}", id);
        Chat chat = chatService.get(id);
        ChatDto chatDto = mapper.convertChatToChatDto(chat);
        return new ResponseEntity<>(chatDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        log.info("delete chat: {}", id);
        chatService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ChatDto>> getAll(@RequestParam(name = "userId") Integer userId) {
        log.info("getAll chat by userId: {}", userId);
        List<Chat> chatList = chatService.getAll(userId);
        List<ChatDto> chatDtoList = Mapper.convertList(chatList, mapper::convertChatToChatDto);
        return new ResponseEntity<>(chatDtoList, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addMessage(@PathVariable(name = "id") Integer idChat,
                                        @RequestBody MessagesDto messagesDto) {
        log.info("addMessage to chat id: {} by userId: {}", idChat, messagesDto.getIdUser());
        chatService.addMessage(messagesDto.getIdUser(), idChat, messagesDto.getContent());
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
