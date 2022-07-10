package lav.valentine.apigeneratingtoken.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message from user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String name;
    private String message;
}