package com.privateadvertisements.dataTest;

import com.privateadvertisements.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class UserTestData {

    public static final User USER_1 = new User(100006,
            Set.of(RoleTestData.ROLE_ADMIN),
            "petr@gmail.com",
            "$2a$10$69Fua.tjM9LMVtkjQTFxHuUqhDEtaQ4nntrzRoLmurPBbhc99/nSS",
            100,
            true,
            LocalDateTime.of(2021, 10, 12, 18, 46, 23),
            AddressDataTest.ADDRESS_1,
            List.of(CreditCartDataTest.CREDIT_CARD_1),
            PersonalUserInfoTestData.PERSONAL_USER_INFO_1,
            Set.of(ChatTestData.CHAT_1)
//            List.of(MessageTestData.MESSAGES_1)
//            List.of(CommentTestData.COMMENT_1),
//            List.of(AdvertisementTestData.ADVERTISEMENT_1)
    );

    public static final User USER_2 = new User(100007,
            Set.of(RoleTestData.ROLE_USER),
            "anna@gmail.com",
            "$2a$10$kkh5EmgxKC7Vrt6E3kXQH.HmWo9t0J6DoG933NU0UjKOm.OuNyKzm",
            100,
            true,
            LocalDateTime.of(2021, 10, 12, 18, 46, 23),
            AddressDataTest.ADDRESS_2,
            List.of(CreditCartDataTest.CREDIT_CARD_2),
            PersonalUserInfoTestData.PERSONAL_USER_INFO_2,
            Set.of(ChatTestData.CHAT_1)
//            List.of(MessageTestData.MESSAGES_2)
//            List.of(CommentTestData.COMMENT_2),
//            List.of(AdvertisementTestData.ADVERTISEMENT_2)
    );


    public static final List<User> USER_LIST_PAGEABLE = List.of(USER_2, USER_1);
    public static final List<User> USER_LIST_ALL = List.of(USER_1, USER_2);

}
