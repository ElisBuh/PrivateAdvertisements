package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.CrudChat;
import com.privateadvertisements.api.repository.CrudMessages;
import com.privateadvertisements.api.repository.CrudUser;
import com.privateadvertisements.api.service.IChatService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.exception.ServiceException;
import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.Messages;
import com.privateadvertisements.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService implements IChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final CrudChat chatRepository;
    private final CrudUser userRepository;
    private final CrudMessages messagesRepository;

    public ChatService(CrudChat chatRepository,
                       CrudUser userRepository,
                       CrudMessages messagesRepository) {
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
            log.info("Creat {}", nameChat);
            chat.setName(nameChat);
            chat.setUsers(userList);
            chatRepository.save(chat);
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                Set<Chat> chatSet = user.getChats();
                chatSet.add(chat);
                user.setChats(chatSet);
                userRepository.save(user);
            }
            return chat;
        } catch (RuntimeException e) {
            log.error("Такой чат есть: {}", Arrays.toString(id));
            throw new NotEntityException("Такой чат есть");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Chat get(Integer id) {
        log.info("get chat id: {}", id);
        Optional<Chat> optionalChat = chatRepository.getWithMessages(id);
        if (optionalChat.isPresent()) {
            return optionalChat.get();
        } else {
            log.error("Такого id: {} нет", id);
            throw new NotEntityException("Такого ид нет " + id);
        }
    }

    @Override
    public void delete(Integer id) {
        log.info("delete chat id: {}", id);
        if (chatRepository.delete(id) == 0) {
            log.error("Не верные данные Id: {}", id);
            throw new NotEntityException("Не верные данные");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> getAll(Integer userId) {
        log.info("getAll chat by userID: {}", userId);
        return chatRepository.findAll().stream()
                .filter(chat -> chat.getUsers().stream().anyMatch(user -> user.getId().equals(userId)))
                .collect(Collectors.toList());
    }


    @Override
    public Messages addMessage(Integer idUser, Integer idChat, String content) {
        try {
            log.info("addMessage by userId: {}, to idChat: {}", idUser, idChat);
            Messages messages = new Messages();
            messages.setUser(userRepository.getById(idUser));
            messages.setChat(chatRepository.getById(idChat));
            messages.setContent(content);
            messages.setDateCreate(LocalDateTime.now());
            return messagesRepository.save(messages);
        } catch (EntityNotFoundException e) {
            log.error("Такого idUser нет {}", idUser);
            throw new NotEntityException("Такого ид нет: " + idUser);
        }
    }

    @Override
    public void deleteMessage(Integer idMessage) {
        try {
            log.info("delete message id: {}", idMessage);
            Messages messages = messagesRepository.getById(idMessage);
            messages.setContent("Сообщение удаленно");
            messagesRepository.save(messages);
        } catch (EntityNotFoundException e) {
            log.error("Такого id нет {}", idMessage);
            throw new NotEntityException("Такого ид нет: " + idMessage);
        }
    }

    @Override
    public Messages editMessage(Integer idMessage, Integer idUser, Integer idChat, String content) {
        try {
            log.info("editMessage id: {}", idMessage);
            Messages messages = messagesRepository.getById(idMessage);
            if (!messages.getChat().getId().equals(idChat) || !messages.getUser().getId().equals(idUser)) {
                throw new ServiceException("сообщение не принадлежит user");
            }
            messages.setContent(content);
            return messagesRepository.save(messages);
        } catch (EntityNotFoundException e) {
            log.error("Такого id нет {}", idMessage);
            throw new NotEntityException("Такого ид нет: " + idMessage);
        }
    }
}
