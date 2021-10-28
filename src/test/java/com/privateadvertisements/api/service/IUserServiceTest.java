package com.privateadvertisements.api.service;

import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.CreditCard;
import com.privateadvertisements.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static com.privateadvertisements.dataTest.AddressDataTest.NEW_ADDRESS;
import static com.privateadvertisements.dataTest.CreditCartDataTest.NEW_CREDIT_CARD;
import static com.privateadvertisements.dataTest.PersonalUserInfoTestData.NEW_PERSONAL_USER_INFO;
import static com.privateadvertisements.dataTest.RoleTestData.ROLE_ADMIN;
import static com.privateadvertisements.dataTest.RoleTestData.ROLE_USER;
import static com.privateadvertisements.dataTest.UserTestData.USER_1;
import static com.privateadvertisements.dataTest.UserTestData.USER_LIST_ALL;
import static com.privateadvertisements.dataTest.UserTestData.USER_LIST_PAGEABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationTest.properties")
@Sql(scripts = "classpath:initDB_hsqldb.sql")
@Sql(scripts = "classpath:populateDB_hsqldb.sql")
public class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void save() {
    }

    @Test
    public void updatePasswords() {
        userService.updatePasswords(100006, "TestTest");
        User user = userService.get(100006);
        assertNotEquals(USER_1, user);
    }

    @Test
    public void updateRating() {
        userService.updateRating(100006, 50);
        User user = userService.get(100006);
        User userUpdate = new User(USER_1);
        userUpdate.setRating(50);
        assertEquals(userUpdate,user);
    }

    @Test
    public void updateStatus() {
        userService.updateStatus(100006, false);
        User user = userService.get(100006);
        User userUpdate = new User(USER_1);
        userUpdate.setEnabled(false);
        assertEquals(userUpdate,user);
    }

    @Test
    public void delete() {
        userService.delete(100006);
        assertThrows(NotEntityException.class,()->userService.get(100006));
    }

    @Test
    public void get() {
        User user = userService.get(100006);
        assertEquals(USER_1, user);
    }

    @Test
    public void findByUserLogin() {
        User user = userService.findByUserLogin("petr@gmail.com");
        assertEquals(USER_1, user);
    }

    @Test
    public void findByUserLoginAndPassword() {
        User user = userService.findByUserLoginAndPassword("petr@gmail.com", "1234");
        assertEquals(USER_1, user);
    }

    @Test
    public void changeRole() {
        User user = userService.changeRole(100006, "ROLE_USER");
        User userUpdate = new User(USER_1);
        userUpdate.setRoles(Set.of(ROLE_ADMIN, ROLE_USER));
        assertEquals(userUpdate.getRoles(),user.getRoles());
    }

    @Test
    public void changeAddress() {
        User user = userService.changeAddress(NEW_ADDRESS,100006);
        User userUpdate = new User(USER_1);
        userUpdate.setAddress(NEW_ADDRESS);
        assertEquals(userUpdate.getAddress().toString(),user.getAddress().toString());
    }

    @Test
    public void changePersonalInfo() {
        User user = userService.changePersonalInfo(NEW_PERSONAL_USER_INFO,100006);
        User userUpdate = new User(USER_1);
        userUpdate.setPersonalUserInfo(NEW_PERSONAL_USER_INFO);
        assertEquals(userUpdate.getPersonalUserInfo().toString(),user.getPersonalUserInfo().toString());
    }

    @Test
    public void changeCreditCard() {
        CreditCard creditCard = userService.changeCreditCard(100006, NEW_CREDIT_CARD);
        assertEquals(NEW_CREDIT_CARD,creditCard);
    }

    @Test
    public void getAllPagesAndSort() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("login"));
        List<User> userList = userService.getAllPagesAndSort(pageable).getContent();
        assertEquals(USER_LIST_PAGEABLE, userList);
    }

    @Test
    public void getAll() {
        List<User> userList = userService.getAll();
        assertEquals(USER_LIST_ALL,userList);
    }

    @Test
    public void getWithAdvertisement() {
        User user = userService.getWithAdvertisement(100006);
        assertEquals(USER_1, user);
    }
}