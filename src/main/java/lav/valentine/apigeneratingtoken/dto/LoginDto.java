package lav.valentine.apigeneratingtoken.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data sent by the user for authentication
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String name;
    private String password;
}