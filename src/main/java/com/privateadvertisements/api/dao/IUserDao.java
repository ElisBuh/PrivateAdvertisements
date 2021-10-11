package com.privateadvertisements.api.dao;

import com.privateadvertisements.model.User;


public interface IUserDao extends IAbstractDao<User> {

    User getByLogin(String login);

}
