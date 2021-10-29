package com.privateadvertisements.dataTest;

import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.Sex;

import java.time.LocalDate;

public class PersonalUserInfoTestData {

    public static final PersonalUserInfo PERSONAL_USER_INFO_1 = new PersonalUserInfo(100003,
            "Петр",
            "Петров",
            LocalDate.of(1989, 1, 24),
            1002003,
            Sex.MALE);
    public static final PersonalUserInfo PERSONAL_USER_INFO_2 = new PersonalUserInfo(100005,
            "Анна",
            "Малинина",
            LocalDate.of(2003, 4, 24),
            5008009,
            Sex.FEMALE);

    public static final PersonalUserInfo NEW_PERSONAL_USER_INFO = new PersonalUserInfo(
            "Анна",
            "Малинина",
            LocalDate.of(2003, 4, 24),
            5008009,
            Sex.FEMALE);
}
