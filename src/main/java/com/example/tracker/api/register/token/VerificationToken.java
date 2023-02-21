package com.example.tracker.api.register.token;

import com.example.tracker.api.register.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private LocalDateTime created;
    private LocalDateTime confirmed;
    private LocalDateTime expired;

    public VerificationToken(String token,
                             User user,
                             LocalDateTime created,
                             LocalDateTime confirmed,
                             LocalDateTime expired) {
        this.token = token;
        this.user = user;
        this.created = created;
        this.confirmed = confirmed;
        this.expired = expired;
    }
}
