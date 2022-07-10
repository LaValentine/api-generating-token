package lav.valentine.apigeneratingtoken.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.UUID;

/**
 * Database table 'user'
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    private UUID userId;
    @NaturalId
    private String name;
    private String password;
}