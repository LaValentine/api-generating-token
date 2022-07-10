package lav.valentine.apigeneratingtoken.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lav.valentine.apigeneratingtoken.dto.LoginRequestDto;
import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.dto.TokenResponseDto;
import lav.valentine.apigeneratingtoken.service.LoginService;
import lav.valentine.apigeneratingtoken.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws JsonProcessingException {
        log.info("Got POST request endpoint: /api/login request body :" + objectMapper.writeValueAsString(loginRequestDto));

        return ResponseEntity.ok(loginService.generateToken(loginRequestDto));
    }

    @PostMapping(value = "/message")
    public ResponseEntity<?> sendMessage(@RequestHeader("Authorization") String token,
                                         @RequestBody MessageDto messageDto) throws JsonProcessingException {
        log.info("Got POST request endpoint: /api/message request body :" + objectMapper.writeValueAsString(messageDto));

        List<MessageDto> messages = messageService.checkMessage(messageDto, token);
        return messages.isEmpty() ? ResponseEntity.ok(messageDto) : ResponseEntity.ok(messages);
    }
}