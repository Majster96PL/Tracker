package com.example.tracker.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String UUID;
    @Column(name = "firstname")
    @NotBlank(message = "Required firstname!")
    private String firstname;
    @Column(name = "lastname")
    @NotBlank(message = "Required lastname!")
    private String lastname;
    @Email
    @NotBlank(message = "Required email!")
    private String email;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean lockedUser;
    private boolean enabledUser;

    public User(String firstname,
                String lastname,
                String email,
                String login,
                String password,
                UserRole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
