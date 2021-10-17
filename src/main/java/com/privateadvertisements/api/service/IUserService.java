package com.privateadvertisements.api.service;

import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    User save(User user);
    void delete(Integer id);
    User get(Integer id);
    User findByUserLogin(String login);
    User findByUserLoginAndPassword(String login, String password);
    User changeRole(String login, String nameRole);
    User changeAddress(Address address, Integer userId);
    User changePersonalInfo(PersonalUserInfo personalUserInfo, Integer userId);

    Page<User> getAllPagesAndSort(Pageable pageable);
    List<User> getAll();

}
