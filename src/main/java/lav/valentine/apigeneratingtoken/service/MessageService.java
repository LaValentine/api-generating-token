package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.MessageDto;

import java.util.List;

/**
 * The class is designed to work with messages sent by users
 */
public interface MessageService {
    List<MessageDto> checkMessage(MessageDto messageDto, String token);
}