package lav.valentine.apigeneratingtoken.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.apigeneratingtoken.controller.UserController;
import lav.valentine.apigeneratingtoken.dto.LoginRequestDto;
import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.exception.handler.ApiExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ApiExceptionHandlerTest {

    @MockBean
    private UserController userController;

    private MockMvc mvc;

    private final String TOKEN = "token";
    private final String USER = "user";
    private final String PASSWORD = "password";
    private final String MESSAGE = "message";
    private final String ERROR_MESSAGE = "error-message";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new ApiExceptionHandler(), userController).build();
    }

    @Test
    void ApiExceptionHandlerNotExistException() throws Exception {
        when(userController.login(any()))
                .thenThrow(new NotExistException(ERROR_MESSAGE));

        LoginRequestDto loginRequestDto = new LoginRequestDto(USER, PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/api/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(content().string(ERROR_MESSAGE))
                .andExpect(status().isBadRequest());
    }
    @Test
    void ApiExceptionHandlerWrongPasswordException() throws Exception {
        when(userController.login(any()))
                .thenThrow(new WrongPasswordException(ERROR_MESSAGE));

        LoginRequestDto loginRequestDto = new LoginRequestDto(USER, PASSWORD);

        mvc.perform(MockMvcRequestBuilders.post("/api/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(ERROR_MESSAGE));
    }

    @Test
    void ApiExceptionHandlerTokenException() throws Exception {
        when(userController.sendMessage(any(), any()))
                .thenThrow(new TokenException(ERROR_MESSAGE));
        MessageDto messageDto = new MessageDto(USER, MESSAGE);

        mvc.perform(MockMvcRequestBuilders.post("/api/message")
                .header("token", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageDto)))
                .andExpect(content().string(ERROR_MESSAGE))
                .andExpect(status().isForbidden());
    }
}