package com.privateadvertisements.dataTest;

import com.privateadvertisements.model.Chat;

import java.util.List;

public class ChatTestData {

    public static final Chat CHAT_1 = new Chat(100000, "Chat");

    public static final List<Chat> CHAT_LIST_USER_1 = List.of(CHAT_1);

    public static final Chat NEW_CHAT = new Chat(100003, "Chat between: petr@gmail.com and anna@gmail.com");
}
