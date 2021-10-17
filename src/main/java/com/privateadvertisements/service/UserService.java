package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.CrudRole;
import com.privateadvertisements.api.repository.CrudUser;
import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.Role;
import com.privateadvertisements.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final CrudUser userRepository;
    private final CrudRole roleRepository;

    public UserService(CrudUser userRepository, CrudRole roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User save(User user) {
        if (user.getId()==null){
        Set<Role> roles = Set.of(roleRepository.getByName("ROLE_USER"));
        user.setRoles(roles);
        user.setRating(50);
        user.setEnabled(true);
        user.setDateRegistered(LocalDate.now());
        return userRepository.save(user);
        }else return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        log.info("delete user id: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public User get(Integer id) {
        log.info("get user id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotEntityException("Такого ид нет");
        }
    }

    @Override
    public User findByUserLogin(String login) {
        log.info("get user login: {}", login);
        return userRepository.getByLogin(login);
    }

    @Override
    public User findByUserLoginAndPassword(String login, String password) {
        User user = userRepository.getByLogin(login);
        if (user != null) {
            return user;
        } else return null;
    }

    @Override
    public User changeRole(String login, String nameRole) {
        Role role = roleRepository.getByName(nameRole.toUpperCase(Locale.ROOT));
        User user = userRepository.getByLogin(login);
        if (user == null || role == null) {
            throw new NotEntityException("Не верные данные");
        }
        Set<Role> userRoles = user.getRoles();
        if (userRoles.contains(role)) {
            userRoles.remove(role);
        } else {
            userRoles.add(role);
        }
        user.setRoles(userRoles);
        return userRepository.save(user);

    }

    @Override
    public User changeAddress(Address address, Integer userId) {
        User user = userRepository.getById(userId);
        if (user == null){
            throw new NotEntityException("Такого ид нет");
        }
        user.setAddress(address);
        return userRepository.save(user);
    }

    @Override
    public User changePersonalInfo(PersonalUserInfo personalUserInfo, Integer userId) {
        return null;
    }

    @Override
    public Page<User> getAllPagesAndSort(Pageable pageable) {
        log.info("getAll pageNumber: {}", pageable.getPageNumber());
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll users");
        return userRepository.findAll();
    }
}
