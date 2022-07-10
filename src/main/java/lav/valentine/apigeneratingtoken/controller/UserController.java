package lav.valentine.apigeneratingtoken.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.apigeneratingtoken.dto.LoginDto;
import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.dto.TokenDto;
import lav.valentine.apigeneratingtoken.service.LoginService;
import lav.valentine.apigeneratingtoken.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The class handles user requests
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final LoginService loginService;
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public UserController(LoginService loginService, MessageService messageService) {
        this.objectMapper = new ObjectMapper();
        this.loginService = loginService;
        this.messageService = messageService;
    }

    /**
     * The method processes the user authentication request
     * @param loginDto Contains user data (name, password)
     * @return Generated jwt token
     * @throws JsonProcessingException
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto)
            throws JsonProcessingException {
        log.info("Got POST request endpoint: /api/login request body:" +
                objectMapper.writeValueAsString(loginDto));

        return ResponseEntity.ok(loginService.userAuthentication(loginDto));
    }

    /**
     * The method processes the user registration request
     * @param loginDto Contains user data (name, password)
     * @return Generated jwt token or user existence message
     * @throws JsonProcessingException
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginDto loginDto)
            throws JsonProcessingException {
        log.info("Got POST request endpoint: /api/register request body:" +
                objectMapper.writeValueAsString(loginDto));

        return loginService.userRegistration(loginDto)
                ? ResponseEntity.ok(loginService.userAuthentication(loginDto))
                : ResponseEntity.ok("The user already exists, try again");
    }

    /**
     * The method processes a request with a message from a user
     * @param token Parameter stored in the 'Authorization' header
     * @param messageDto Contains data (name, message)
     * @return Saved message or message list
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/message")
    public ResponseEntity<?> sendMessage(@RequestHeader("Authorization") String token,
                                         @RequestBody MessageDto messageDto) throws JsonProcessingException {
        log.info("Got POST request endpoint: /api/message request body:" + objectMapper.writeValueAsString(messageDto));

        List<MessageDto> messages = messageService.checkMessage(messageDto, token);
        return messages.isEmpty() ? ResponseEntity.ok(messageDto) : ResponseEntity.ok(messages);
    }
}