package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudUser extends JpaRepository<User, Integer> {


    User getByLogin(String login);

}
