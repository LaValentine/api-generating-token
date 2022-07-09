package lav.valentine.apigeneratingtoken.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Message {
    @Id
    private UUID messageId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String message;
}