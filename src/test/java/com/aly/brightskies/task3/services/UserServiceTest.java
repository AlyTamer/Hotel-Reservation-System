package com.aly.brightskies.task3.services;

import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional

public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserName("Aly");
        testUser.setEmail("ali@example.com");
        testUser.setNumber(123456);
        testUser.setPassword("pass");
        testUser.setRole(Role.ROLE_USER);
        userRepo.save(testUser);
    }

    @Test
    void testUpdateUserRole() {
        try {
            userService.updateUserRole(testUser.getId(), Role.ROLE_ADMIN);
            User updatedUser = userRepo.findById(testUser.getId()).orElse(null);
            assertNotNull(updatedUser);
            assertTrue(updatedUser.getRole() == Role.ROLE_ADMIN);
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    void testFindAllUsers() {
        try {
            List<UserDTO> users = userService.findAll();
            assertNotNull(users);
            assertTrue(users.size() > 0);
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
    @Test
    void testDeleteUserById() {
        try {
            userService.deleteById(testUser.getId());
            User deletedUser = userRepo.findById(testUser.getId()).orElse(null);
            assertNull(deletedUser);
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }

    }
    @Test
    void testUpdateRole_UserNotFound() {
        try {
            userService.updateUserRole(9999, Role.ROLE_ADMIN); // Assuming 9999 is an invalid ID
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("User not found"));
        }
    }
    @Test
    void testFindAllUsers_EmptyList() {
        try {
            userRepo.deleteAll(); // Clear the repository
            List<UserDTO> users = userService.findAll();
            assertTrue(users==null, "Expected an empty list of users");
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
    @Test
    void testDeleteUserById_UserNotFound() {
        try {
            userService.deleteById(9999); // Assuming 9999 is an invalid ID
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            return;
        }
    }
}
