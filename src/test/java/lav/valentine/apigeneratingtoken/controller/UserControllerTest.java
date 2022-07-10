package lav.valentine.apigeneratingtoken.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.apigeneratingtoken.dto.LoginRequestDto;
import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.dto.TokenResponseDto;
import lav.valentine.apigeneratingtoken.service.LoginService;
import lav.valentine.apigeneratingtoken.service.MessageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private LoginService loginService;
    @MockBean
    private MessageService messageService;

    @Autowired
    private UserController userController;

    private final String USER = "user";
    private final String PASSWORD = "password";
    private final String MESSAGE = "message";
    private final String TOKEN = "token";

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void login() throws Exception {
        TokenResponseDto tokenResponseDto = new TokenResponseDto(TOKEN);
        LoginRequestDto loginRequestDto = new LoginRequestDto(USER, PASSWORD);

        when(loginService.generateToken(any())).thenReturn(tokenResponseDto);

        mvc.perform(MockMvcRequestBuilders.post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(tokenResponseDto)));
    }

    @Test
    void sendMessageReturnedEmpty() throws Exception {
        when(messageService.checkMessage(any(), any())).thenReturn(Collections.emptyList());
        MessageDto messageDto = new MessageDto(USER, MESSAGE);

        mvc.perform(MockMvcRequestBuilders.post("/api/message")
                    .header("Authorization", TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(messageDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(content().string(objectMapper.writeValueAsString(messageDto)));;
    }

    @Test
    void sendMessageReturnedList() throws Exception {
        MessageDto messageDto = new MessageDto(USER, MESSAGE);
        List<MessageDto> messages = List.of(messageDto, messageDto, messageDto);


        when(messageService.checkMessage(any(), any())).thenReturn(messages);

        mvc.perform(MockMvcRequestBuilders.post("/api/message")
                    .header("Authorization", TOKEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(messageDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(content().string(objectMapper.writeValueAsString(messages)));
    }
}