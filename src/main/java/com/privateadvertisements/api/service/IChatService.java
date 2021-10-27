package com.privateadvertisements.api.service;

import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.Messages;

import java.util.List;

public interface IChatService {

    Chat creat(Integer... id);

    Chat get(Integer id);

    Messages addMessage(Integer idUser, Integer idChat, String content);

    void delete(Integer id);

    void deleteMessage(Integer idMessage);

    Messages editMessage(Integer idMessage, Integer idUser, Integer idChat, String content);

    List<Chat> getAll(Integer userId);


}
