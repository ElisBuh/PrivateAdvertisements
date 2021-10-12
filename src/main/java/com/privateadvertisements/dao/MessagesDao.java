package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudMessages;
import com.privateadvertisements.model.Messages;

import java.util.List;

public class MessagesDao implements IAbstractDao<Messages> {

    private final CrudMessages crudMessages;

    public MessagesDao(CrudMessages crudMessages) {
        this.crudMessages = crudMessages;
    }

    @Override
    public Messages save(Messages messages) {
        return crudMessages.save(messages);
    }

    @Override
    public void delete(Integer id) {
        crudMessages.deleteById(id);
    }

    @Override
    public Messages get(Integer id) {
        return crudMessages.getById(id);
    }

    @Override
    public List<Messages> getAll() {
        return crudMessages.findAll();
    }
}
