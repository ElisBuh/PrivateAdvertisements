package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudUser extends JpaRepository<User, Integer> {


    User getByLogin(String login);

}
