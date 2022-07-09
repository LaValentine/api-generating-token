package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.entity.Message;
import lav.valentine.apigeneratingtoken.entity.User;
import lav.valentine.apigeneratingtoken.repository.MessageRepository;
import lav.valentine.apigeneratingtoken.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final String TOKEN = "token";
    private final UUID ID = UUID.randomUUID();
    private final String USER = "user";
    private final String PASSWORD = "password";
    private final String SOME_MESSAGE = "message";

    @Value("${app.message-getting-first-10}")
    private String MESSAGE_GETTING_FIRST_10;

    @Test
    void checkMessageReturnedFirstMessages() {
        MessageDto messageDto = new MessageDto(USER, MESSAGE_GETTING_FIRST_10);
        User user = new User(ID, USER, PASSWORD);
        List<Message> listMessages = List.of(
                new Message(ID, user, SOME_MESSAGE),
                new Message(ID, user, SOME_MESSAGE));

        when(tokenService.tokenIsValid(any(), any())).thenReturn(true);

        when(messageRepository.findTop10ByUser(userService.getUserByName(USER))).thenReturn(listMessages);

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