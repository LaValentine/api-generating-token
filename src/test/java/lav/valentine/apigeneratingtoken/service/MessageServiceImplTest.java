package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.entity.Message;
import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.repository.MessageRepository;
import lav.valentine.apigeneratingtoken.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class MessageServiceImplTest {

    @MockBean
    private MessageRepository messageRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private TokenService tokenService;

    @Autowired
    private MessageServiceImpl messageService;

    private final static String TOKEN = "token";
    private final static UUID ID = UUID.randomUUID();
    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String SOME_MESSAGE = "message";
    private final static String MESSAGE_GETTING_FIRST = "message-getting-first";

    @Test
    void checkMessageReturnedFirstMessages() {
        MessageDto messageDto = new MessageDto(USER, MESSAGE_GETTING_FIRST);
        User user = new User(ID, USER, PASSWORD);
        List<Message> listMessages = List.of(
                new Message(ID, user, SOME_MESSAGE),
                new Message(ID, user, SOME_MESSAGE));

        when(tokenService.tokenIsValid(any(), any())).thenReturn(true);

        when(messageRepository.findAllByUser(userService.getUserByName(USER))).thenReturn(listMessages);

        messageService.checkMessage(messageDto, TOKEN).forEach(m -> {
            assertEquals(m.getName(), USER);
            assertEquals(m.getMessage(), SOME_MESSAGE);
        });

    }

    @Test
    void checkMessageToSave() {
        MessageDto messageDto = new MessageDto(USER, SOME_MESSAGE);
        when(tokenService.tokenIsValid(any(), any())).thenReturn(true);

        assertTrue(messageService.checkMessage(messageDto, TOKEN).isEmpty());
    }

    @Test
    void checkMessageTokenInvalid() {
        MessageDto messageDto = new MessageDto(USER, SOME_MESSAGE);
        when(tokenService.tokenIsValid(any(), any())).thenReturn(false);

        assertTrue(messageService.checkMessage(messageDto, TOKEN).isEmpty());
    }
}