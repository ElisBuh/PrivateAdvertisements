package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudUser extends PagingAndSortingRepository<User, Integer> {


    User getByLogin(String login);

}
