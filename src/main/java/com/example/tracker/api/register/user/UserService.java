package com.example.tracker.api.register.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
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

    public int enabledUser(String email){
        return userRepository.enabledUser(email);
    }

}
