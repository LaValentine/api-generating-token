package lav.valentine.apigeneratingtoken.service.impl;

import lav.valentine.apigeneratingtoken.dto.MessageDto;
import lav.valentine.apigeneratingtoken.entity.Message;
import lav.valentine.apigeneratingtoken.exception.NotExistException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @Value("${exception.message.not-exist}")
    private String MESSAGE_NOT_EXIST;

    private final static String REGEX_GETTING_MESSAGE_HISTORY = "^history\\s[\\d]*$";
    private final static String REGEX_HOW_MANY_MESSAGES = "\\b[\\d]+\\b";

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
        // verification of the validity of the token
        if (tokenService.tokenIsValid(token, messageDto.getName())) {

            String message = messageDto.getMessage();

            // checking whether the user requests the message history
            if (Pattern.matches(REGEX_GETTING_MESSAGE_HISTORY, message)) {
                return getMessageHistory(messageDto.getMessage(), messageDto.getName());
            }
            log.info("Saving message from " + messageDto.getName());

            // if the user does not request the message history, message will be saved
            saveMessage(messageDto);
        }
        return Collections.emptyList();
    }

    private void saveMessage(MessageDto messageDto) {
        log.info("Saving message from " + messageDto.getName());

        messageRepository.save(Message.builder()
                .messageId(UUID.randomUUID())
                .user(userService.getUserByName(messageDto.getName()))
                .message(messageDto.getMessage())
                .build());
    }

    private List<MessageDto> getMessageHistory(String message, String username) {
        Matcher matcherHowManyMessages = Pattern.compile(REGEX_HOW_MANY_MESSAGES).matcher(message);

        int howManyMessages = 0;

        if (matcherHowManyMessages.find()) {
            howManyMessages = Integer.parseInt(message
                    .substring(matcherHowManyMessages.start(), matcherHowManyMessages.end()));
        }

        log.info("Getting first " + howManyMessages + " messages from " + username);

        List<Message> messages = messageRepository
                .findAllByUser(userService.getUserByName(username));

        if (messages.isEmpty()) {
            throw new NotExistException(MESSAGE_NOT_EXIST);
        }
        int howToSkip = messages.size() - howManyMessages;

        return howToSkip > 0
                ? messages.stream().skip(howToSkip)
                .map(m -> new MessageDto(m.getUser().getName(), m.getMessage()))
                .collect(Collectors.toList())
                : messages.stream()
                .map(m -> new MessageDto(m.getUser().getName(), m.getMessage()))
                .collect(Collectors.toList());
    }
}