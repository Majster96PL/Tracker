package com.example.tracker.user;

import com.example.tracker.api.register.role.RoleRepository;
import com.example.tracker.api.register.user.User;
import com.example.tracker.api.register.user.UserRepository;
import com.example.tracker.api.register.user.UserRole;
import com.example.tracker.web.PasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")

public class UserDAOTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRole userRole;

    @Before
    public void setUp(){
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode("testPassword"));
        user.setEmail("testUser@testUser.com");
        user.setRoles(new HashSet<>(Arrays.asList(UserRole.ADMIN, UserRole.USER)));

        userRepository.save(user);
    }

    @Test
    public void testAddUser(){
        User testUser = new User();
        testUser.setUsername("Test");
        testUser.setPassword(passwordEncoder.bCryptPasswordEncoder().encode("test"));
        testUser.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(UserRole.USER))));
        userRepository.save(testUser);

        assertThat(testUser.getId()).isNotNull();
        assertThat(testUser.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(testUser.getPassword()).isEqualTo(testUser.getPassword());

        User retrievedUser = userRepository.findById(testUser.getId()).orElse(null);
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(retrievedUser.getEmail()).isEqualTo(testUser.getEmail());
    }

}
