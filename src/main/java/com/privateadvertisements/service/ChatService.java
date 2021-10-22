package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.CrudChat;
import com.privateadvertisements.api.repository.CrudMessages;
import com.privateadvertisements.api.repository.CrudUser;
import com.privateadvertisements.api.service.IChatService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.Messages;
import com.privateadvertisements.model.User;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final CrudChat chatRepository;
    private final CrudUser userRepository;
    private final CrudMessages messagesRepository;

    public ChatService(CrudChat chatRepository, CrudUser userRepository, CrudMessages messagesRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public Chat creat(Integer... id) {
        try {
        Chat chat = new Chat();
        List<User> userList = Arrays.stream(id).map(userRepository::getById).collect(Collectors.toList());
        String nameChat = "Chat between: " + userList.get(0).getLogin() + " and " + userList.get(1).getLogin();
        log.info(nameChat);
        chat.setName(nameChat);
        chat.setUsers(userList);
        return chatRepository.save(chat);
        } catch (RuntimeException e){
            throw new NotEntityException("Такой чат есть");
        }
    }

    @Override
    public Chat get(Integer id) {
        log.info("get chat id: {}", id);
        Optional<Chat> optionalChat = chatRepository.findById(id);
        if (optionalChat.isPresent()) {
            return optionalChat.get();
        } else {
            log.error("Такого id: {} нет", id);
            throw new NotEntityException("Такого ид нет " + id);
        }
    }

    @Override
    public Messages addMessage(Integer idUser, Integer idChat, String content) {
        Messages messages = new Messages();
        messages.setUser(userRepository.getById(idUser));
        messages.setChat(chatRepository.getById(idChat));
        messages.setContent(content);
        messages.setDateCreate(LocalDateTime.now());
        return messagesRepository.save(messages);
    }

    @Override
    public void deleteMessage(Integer idMessage) {
        Messages messages = messagesRepository.getById(idMessage);
        messages.setContent("Сообщение удаленно");
        messagesRepository.save(messages);
    }

    @Override
    public Messages editMessage(Integer idMessage, String content) {
        Messages messages = messagesRepository.getById(idMessage);
        messages.setContent(content);
        return messagesRepository.save(messages);
    }
}
