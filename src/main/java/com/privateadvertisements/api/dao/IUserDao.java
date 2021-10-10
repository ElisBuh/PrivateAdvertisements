package com.privateadvertisements.api.dao;

import com.privateadvertisements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUserDao extends JpaRepository<User, Integer> {

//
//    User create(User user);
//    User get(Integer id);
//    User update(User user);
//    boolean delete(Integer id);


}
