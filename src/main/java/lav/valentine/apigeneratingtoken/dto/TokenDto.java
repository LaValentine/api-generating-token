package lav.valentine.apigeneratingtoken.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token for user authorization
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String token;
}