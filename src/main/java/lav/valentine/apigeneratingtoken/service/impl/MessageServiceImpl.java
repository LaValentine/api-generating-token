package lav.valentine.apigeneratingtoken.service.impl;

import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.entity.Message;
import lav.valentine.apigeneratingtoken.repository.MessageRepository;
import lav.valentine.apigeneratingtoken.service.MessageService;
import lav.valentine.apigeneratingtoken.service.TokenService;
import lav.valentine.apigeneratingtoken.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The class is designed to work with messages sent by users
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final TokenService tokenService;

    @Value("${app.message-getting-first-10}")
    private String MESSAGE_GETTING_FIRST_10;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService, TokenService tokenService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * The method processes messages sent by the user
     * @param messageDto Data sent by the user (name, message)
     * @param token User's access token
     * @return Saved message or list messages
     */
    @Override
    public List<MessageDto> checkMessage(MessageDto messageDto, String token) {
        log.info("Checking message from " + messageDto.getName());

        if (tokenService.tokenIsValid(token, messageDto.getName())) {
            if (messageDto.getMessage().equals(MESSAGE_GETTING_FIRST_10)) {
                log.info("Getting first 10 messages from " + messageDto.getName());
                return messageRepository.findTop10ByUser(userService.getUserByName(messageDto.getName())).stream()
                        .map(m -> new MessageDto(m.getUser().getName(), m.getMessage())).collect(Collectors.toList());
            }
            else {
                log.info("Saving message from " + messageDto.getName());
                messageRepository.save(Message.builder()
                        .messageId(UUID.randomUUID())
                        .user(userService.getUserByName(messageDto.getName()))
                        .message(messageDto.getMessage())
                        .build());
            }
        }
        return Collections.emptyList();
    }
}