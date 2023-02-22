package com.example.tracker.api.register.user;

import com.example.tracker.api.register.token.VerificationToken;
import com.example.tracker.api.register.token.VerificationTokenService;
import com.example.tracker.exceptions.UserExceptions;
import com.example.tracker.web.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;
    private static final String MESSAGE = "USER NOT FOUND!";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.orElseThrow(
                () -> new UsernameNotFoundException(String.format(MESSAGE, username))

        );
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                user.getAuthorities()
        );
    }

    public String getUser(User user){
        boolean isUserExists =
                userRepository.findByUsername(user.getUsername()).isPresent();
        if (isUserExists){
            throw new UserExceptions(MESSAGE, 500);
        }

        String password = passwordEncoder.bCryptPasswordEncoder()
                .encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token,
                user,
                LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now()
        );
        verificationTokenService.saveToken(verificationToken);
        return token;
    }

    public int enabledUser(String email){
        return userRepository.enabledUser(email);
    }

}
