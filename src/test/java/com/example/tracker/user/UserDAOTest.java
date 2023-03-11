package com.example.tracker.user;

import com.example.tracker.api.register.role.Role;
import com.example.tracker.api.register.role.RoleRepository;
import com.example.tracker.api.register.user.User;
import com.example.tracker.api.register.user.UserRepository;
import com.example.tracker.api.register.user.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserDAOTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Role roleAdmin, roleUser;

    @Before
    public void setUp(){
        roleAdmin = testEntityManager.persist(new Role(UserRole.ADMIN));
        roleUser = testEntityManager.persist(new Role(UserRole.USER));

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
    }

    private Set<Role> createRolesWithoutAdmin(){
        return UserRole.stream()
                .map(role -> testEntityManager.persist(new Role(role)))
                .filter(role -> role.getRole() != UserRole.ADMIN)
                .collect(Collectors.toSet());
    }

    @Test
    public void whenSaveAndRetrieveUser_thenOK(){
        Set<Role> roles = createRolesWithoutAdmin();

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setEmail("test@test.com");
        testUser.setPassword("test");
        testUser.setRoles(roles);
        testUser.setLocked(false);
        testUser.setEnabled(true);

        userRepository.save(testUser);
        Optional<User> foundUserInRepository = userRepository.findByUsername("testUser");
        assertTrue(foundUserInRepository.isPresent());

        User foundUser = foundUserInRepository.get();
        assertEquals(testUser.getUsername(), foundUser.getUsername());
        assertEquals(testUser.getEmail(), foundUser.getEmail());
        assertEquals(testUser.getPassword(), foundUser.getPassword());
        assertEquals(testUser.getRoles(), foundUser.getRoles());
        assertEquals(testUser.isLocked(), foundUser.isLocked());
        assertEquals(testUser.isEnabled(), foundUser.isEnabled());
    }

    @After
    public void tearDown(){
        testEntityManager.remove(roleAdmin);
        testEntityManager.remove(roleUser);
    }
}
