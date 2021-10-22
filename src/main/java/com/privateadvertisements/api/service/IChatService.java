package com.privateadvertisements.api.service;

import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.Messages;

public interface IChatService {

    Chat creat(Integer...id);
    Chat get(Integer id);
    Messages addMessage(Integer idUser, Integer idChat, String content);
    void deleteMessage(Integer idMessage);
    Messages editMessage(Integer idMessage, String content);

}
