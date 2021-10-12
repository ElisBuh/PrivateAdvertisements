package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudChat;
import com.privateadvertisements.model.Chat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDao implements IAbstractDao<Chat> {

    private final CrudChat crudChat;

    public ChatDao(CrudChat crudChat) {
        this.crudChat = crudChat;
    }

    @Override
    public Chat save(Chat chat) {
        return crudChat.save(chat);
    }

    @Override
    public void delete(Integer id) {
        crudChat.deleteById(id);
    }

    @Override
    public Chat get(Integer id) {
        return crudChat.getById(id);
    }

    @Override
    public List<Chat> getAll() {
        return crudChat.findAll();
    }
}
