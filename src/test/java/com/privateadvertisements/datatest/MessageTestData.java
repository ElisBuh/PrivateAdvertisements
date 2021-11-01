package com.privateadvertisements.datatest;

import com.privateadvertisements.model.Messages;

import java.time.LocalDateTime;

public class MessageTestData {
    public static final Messages MESSAGES_1 = new Messages(100001,
            UserTestData.USER_1,
            ChatTestData.CHAT_1,
            "Hi",
            LocalDateTime.of(2021, 10, 12, 18, 46, 23));

    public static final Messages MESSAGES_2 = new Messages(100002,
            UserTestData.USER_2,
            ChatTestData.CHAT_1,
            "Bye",
            LocalDateTime.of(2021, 10, 12, 18, 46, 23));
    public static final Messages NEW_MESSAGES = new Messages(100003,
            UserTestData.USER_1,
            ChatTestData.CHAT_1,
            "Test",
            LocalDateTime.of(2021, 10, 12, 18, 46, 23));

    public static final Messages UPDATE_MESSAGES = new Messages(100001,
            UserTestData.USER_1,
            ChatTestData.CHAT_1,
            "Test",
            LocalDateTime.of(2021, 10, 12, 18, 46, 23));
}
