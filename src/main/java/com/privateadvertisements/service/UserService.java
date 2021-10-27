package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.CrudAddress;
import com.privateadvertisements.api.repository.CrudCity;
import com.privateadvertisements.api.repository.CrudCountry;
import com.privateadvertisements.api.repository.CrudCreditCard;
import com.privateadvertisements.api.repository.CrudPersonalUserInfo;
import com.privateadvertisements.api.repository.CrudRole;
import com.privateadvertisements.api.repository.CrudUser;
import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.exception.ServiceException;
import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.CreditCard;
import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.Role;
import com.privateadvertisements.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
    private final CrudCountry countryRepository;
    private final CrudCity cityRepository;
    private final CrudAddress addressRepository;
    private final CrudPersonalUserInfo personalUserInfoRepository;
    private final CrudCreditCard creditCardRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(CrudUser userRepository,
                       CrudRole roleRepository,
                       CrudCountry countryRepository,
                       CrudCity cityRepository,
                       CrudAddress addressRepository,
                       CrudPersonalUserInfo personalUserInfoRepository,
                       CrudCreditCard creditCardRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
        this.personalUserInfoRepository = personalUserInfoRepository;
        this.creditCardRepository = creditCardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        log.info("Create new user: {}", user.getLogin());
        User userUpdate = userRepository.getByLogin(user.getLogin());
        if (userUpdate == null) {
            Set<Role> roles = Set.of(roleRepository.getByName("ROLE_USER"));
            user.setRoles(roles);
            user.setRating(100);
            user.setEnabled(true);
            user.setDateRegistered(LocalDateTime.now());
            user.setPasswords(passwordEncoder.encode(user.getPasswords()));
            return userRepository.save(user);
        } else {
            throw new ServiceException("Логин занят");
        }
    }

    @Override
    public User updatePasswords(User user) {
        log.info("update password user: {}", user.getLogin());
        User userUpdate = userRepository.getByLogin(user.getLogin());
        userUpdate.setPasswords(passwordEncoder.encode(user.getPasswords()));
        return userRepository.save(userUpdate);
    }

    @Override
    public void updateRating(Integer id, Integer rating) {
        log.info("Update rating to id {},{}", id, rating);
        try {
            User user = userRepository.getById(id);
            user.setRating(rating);
            userRepository.save(user);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет");
        }

    }


    @Override
    public void updateStatus(Integer id, boolean status) {
        log.info("Update status to id: {}, on {}", id, status);
        try {
            User user = userRepository.getById(id);
            user.setEnabled(status);
            userRepository.save(user);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет");
        }

    }

    @Override
    public void delete(Integer id) {
        log.info("delete user id: {}", id);
        if (userRepository.delete(id) == 0) {
            log.error("Не верные данные Id: {}", id);
            throw new NotEntityException("Не верные данные");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User get(Integer id) {
        log.info("get user id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            log.error("Такого id: {} нет", id);
            throw new NotEntityException("Такого ид нет " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserLogin(String login) {
        log.info("get user login: {}", login);
        try {
            User user = userRepository.getByLogin(login);
            log.info(user.getLogin());
            return userRepository.getByLogin(login);
        } catch (EntityNotFoundException e) {
            log.error("Такого пользователя нет {}", login);
            throw new NotEntityException("Такого пользователя нет: " + login);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserLoginAndPassword(String login, String password) {
        try {
            log.info(login);
            User user = userRepository.getByLogin(login);
            if (user != null) {
                if (passwordEncoder.matches(password, user.getPasswords())) {
                    return user;
                }
            }
            return null;
        } catch (EntityNotFoundException e) {
            log.error("Не верные дынные");
            throw new NotEntityException("Не верные дынные");
        }
    }

    @Override
    public User changeRole(Integer id, String nameRole) {
        log.info("Change role to {} on {}", id, nameRole);
        try {
            Role role = roleRepository.getByName(nameRole.toUpperCase(Locale.ROOT));
            User user = userRepository.getById(id);
            Set<Role> userRoles = user.getRoles();
            if (userRoles.contains(role)) {
                userRoles.remove(role);
            } else {
                userRoles.add(role);
            }
            user.setRoles(userRoles);
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            log.error("Такого пользователя нет {}", id);
            throw new NotEntityException("Такого пользователя нет: " + id);
        }

    }

    @Override
    public User changeAddress(Address address, Integer userId) {
        log.info("Change address to id: {} ", userId);
        try {
            User user = userRepository.getById(userId);
            if (user.getAddress() != null) {
                address.setId(user.getAddress().getId());
            }
            address.setCountry(countryRepository.getByName(address.getCountry().getName()));
            address.setCity(cityRepository.getByName(address.getCity().getName()));
            user.setAddress(addressRepository.save(address));
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", userId);
            throw new NotEntityException("Такого ид нет: " + userId);
        }
    }

    @Override
    public User changePersonalInfo(PersonalUserInfo personalUserInfo, Integer userId) {
        log.info("Change personal info to id: {} ", userId);
        try {
            User user = userRepository.getById(userId);
            user.setPersonalUserInfo(personalUserInfoRepository.save(personalUserInfo));
            return user;
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", userId);
            throw new NotEntityException("Такого ид нет: " + userId);
        }
    }

    @Override
    public CreditCard changeCreditCard(Integer userId, CreditCard creditCard) {
        log.info("Add credit card № {} to userId: {}", creditCard.getNumber(), userId);
        try {
            User user = userRepository.getById(userId);
            boolean checkCard = user.getCreditCards().stream().anyMatch(card -> card.getNumber().equals(creditCard.getNumber()));
            if (!checkCard) {
                creditCard.setUser(user);
                return creditCardRepository.save(creditCard);
            } else {
                throw new ServiceException("Такая карты существует");
            }
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", userId);
            throw new NotEntityException("Такого ид нет: " + userId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getAllPagesAndSort(Pageable pageable) {
        log.info("getAll pageNumber: {}", pageable);
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User getWithAdvertisement(Integer id) {
        log.info("getWithAdvertisement id user: {}", id);
        try {
            User user = userRepository.getWithAdvertisement(id);
            log.info(user.getLogin());
            return user;
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        log.info("getAll users");
        return userRepository.findAll();
    }


}
