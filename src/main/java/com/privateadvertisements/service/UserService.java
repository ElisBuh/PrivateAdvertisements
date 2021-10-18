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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    private final CrudCountry countryRepository;
    private final CrudCity cityRepository;
    private final CrudAddress addressRepository;
    private final CrudPersonalUserInfo personalUserInfoRepository;
    private final CrudCreditCard creditCardRepository;

    public UserService(CrudUser userRepository,
                       CrudRole roleRepository,
                       CrudCountry countryRepository,
                       CrudCity cityRepository,
                       CrudAddress addressRepository,
                       CrudPersonalUserInfo personalUserInfoRepository,
                       CrudCreditCard creditCardRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
        this.personalUserInfoRepository = personalUserInfoRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public User save(User user) {
        log.info("Create new user: {}", user.getLogin());
        User userUpdate = userRepository.getByLogin(user.getLogin());
        if (userUpdate.getId() == null) {
            Set<Role> roles = Set.of(roleRepository.getByName("ROLE_USER"));
            user.setRoles(roles);
            user.setRating(50);
            user.setEnabled(true);
            user.setDateRegistered(LocalDate.now());
            return userRepository.save(user);
        } else {
            throw new ServiceException("Логин занят");
        }
    }

    @Override
    public User updatePasswords(User user) {
        return null;
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
        try {
            User user = userRepository.getById(id);
            userRepository.delete(user);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }
    }

    @Override
    public User get(Integer id) {
        log.info("get user id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            log.error("Такого id: {} нет", id);
            throw new NotEntityException("Такого ид нет" + id);
        }
    }

    @Override
    public User findByUserLogin(String login) {
        log.info("get user login: {}", login);
        try {
            return userRepository.getByLogin(login);
        } catch (EntityNotFoundException e) {
            log.error("Такого пользователя нет {}", login);
            throw new NotEntityException("Такого пользователя нет: " + login);
        }
    }

    @Override
    public User findByUserLoginAndPassword(String login, String password) {

        return null;
    }

    @Override
    public User changeRole(String login, String nameRole) {
        log.info("Change role to {} on {}", login, nameRole);
        try {
            Role role = roleRepository.getByName(nameRole.toUpperCase(Locale.ROOT));
            User user = userRepository.getByLogin(login);
            Set<Role> userRoles = user.getRoles();
            if (userRoles.contains(role)) {
                userRoles.remove(role);
            } else {
                userRoles.add(role);
            }
            user.setRoles(userRoles);
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            log.error("Такого пользователя нет {}", login);
            throw new NotEntityException("Такого пользователя нет: " + login);
        }

    }

    @Override
    public User changeAddress(Address address, Integer userId) {
        log.info("Change address to id: {} ", userId);
        try {
            User user = userRepository.getById(userId);
            if (user.getAddress().getId() != null) {
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
    public Page<User> getAllPagesAndSort(Pageable pageable) {
        log.info("getAll pageNumber: {}", pageable.getPageNumber());
        return userRepository.findAll(pageable);
    }

    @Override
    public User getWithAdvertisement(Integer id) {
        log.info("getWithAdvertisement id user: {}", id);
        try {
            return userRepository.getWithAdvertisement(id);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }
    }

    @Override
    public List<User> getAll() {
        log.info("getAll users");
        return userRepository.findAll();
    }


}
