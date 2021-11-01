package com.privateadvertisements.api.service;

import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.Messages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.privateadvertisements.datatest.ChatTestData.CHAT_1;
import static com.privateadvertisements.datatest.ChatTestData.CHAT_LIST_USER_1;
import static com.privateadvertisements.datatest.ChatTestData.NEW_CHAT;
import static com.privateadvertisements.datatest.MessageTestData.MESSAGES_1;
import static com.privateadvertisements.datatest.MessageTestData.NEW_MESSAGES;
import static com.privateadvertisements.datatest.MessageTestData.UPDATE_MESSAGES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationTest.properties")
@Sql(scripts = "classpath:initDB_hsqldb.sql", config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:populateDB_hsqldb.sql", config = @SqlConfig(encoding = "UTF-8"))
public class IChatServiceTest {

    @Autowired
    private IChatService chatService;

    @Test
    public void creat() {
        Chat chat = chatService.creat(100006, 100007);
        assertEquals(NEW_CHAT, chat);
    }

    @Test
    public void get() {
        Chat chat = chatService.get(100000);
        assertEquals(CHAT_1, chat);
    }

    @Test
    public void getMessage() {
        Messages messages = chatService.getMessage(100001);
        assertEquals(MESSAGES_1.toString(), messages.toString());
    }

    @Test
    public void addMessage() {
        Messages messages = chatService.addMessage(100006, 100000, "Test");
        assertEquals(NEW_MESSAGES.toString(), messages.toString());
    }

    @Test
    public void delete() {
        chatService.delete(100000);
        assertThrows(NotEntityException.class, () -> chatService.get(100001));
    }

    @Test
    public void deleteMessage() {
        chatService.deleteMessage(100001);
        assertThrows(NotEntityException.class, () -> chatService.getMessage(100000));

    }

    @Test
    public void editMessage() {
        Messages messages = chatService.editMessage(100001, 100006, 100000, "Test");
        assertEquals(UPDATE_MESSAGES.toString(), messages.toString());
    }

    @Test
    public void getAll() {
        List<Chat> chatList = chatService.getAll(100006);
        assertEquals(CHAT_LIST_USER_1, chatList);
    }
}