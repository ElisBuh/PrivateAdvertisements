package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IUserDao;
import com.privateadvertisements.api.dataJpa.CrudUser;
import com.privateadvertisements.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements IUserDao {

    private final CrudUser crudUser;

    public UserDao(CrudUser crudUser) {
        this.crudUser = crudUser;
    }

    @Override
    public User save(User user) {
        return crudUser.save(user);
    }

    @Override
    public void delete(Integer id) {
        crudUser.deleteById(id);
    }

    @Override
    public User get(Integer id) {
        return crudUser.findById(id).get();
    }

    @Override
    public User getByLogin(String login) {
        return crudUser.getByLogin(login);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) crudUser.findAll();
    }
}
