package lav.valentine.apigeneratingtoken.service;

import lav.valentine.apigeneratingtoken.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> checkMessage(MessageDto messageDto, String token);
}