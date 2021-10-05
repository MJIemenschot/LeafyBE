package com.example.xedd.service;

import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.Authority;
import com.example.xedd.model.User;
import com.example.xedd.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

        @Mock
        UserRepository userRepository;

        @Mock
        PasswordEncoder passwordEncoder;

        @InjectMocks
        private UserServiceImpl userServiceImpl;

        @Captor
        ArgumentCaptor<User> userCaptor;

        @Test
        public void getUsersTest() {
            when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
            List<User> userList = userServiceImpl.getUsers();
            assertEquals(2, userList.size());
        }

        @Test
        public void getUserTest() {
            User user = new User();
            user.setUsername("Maria");
            String username = user.getUsername();
            when(userRepository.findById(username)).thenReturn(Optional.of(user));
            Optional<User> userOptional = userServiceImpl.getUser(username);
            assertTrue(userOptional.isPresent());
            assertEquals(username, userOptional.get().getUsername());
        }

        @Test
        public void userExistsTest() {
            User user = new User();
            user.setUsername("Maria");
            String username = user.getUsername();
            when(userRepository.existsById(username)).thenReturn(true);
            assertTrue(userRepository.existsById(username));
        }

        @Test
        public void createUserTest() {
            User theUser = new User();
            theUser.setUsername("Maria");
            theUser.setPassword(passwordEncoder.encode("1234567"));
            String passwordForm = passwordEncoder.encode(theUser.getPassword());
            when(userRepository.save(theUser)).thenReturn(theUser);
            userServiceImpl.createUser(theUser);
            verify(userRepository).save(userCaptor.capture());
            User newUser = userCaptor.getValue();
            assertThat(theUser.getUsername().equals(newUser.getUsername()));
        }

        @Test
        public void deleteUserTest() {
            User user = new User();
            user.setUsername("Maria");
            userServiceImpl.deleteUser(user.getUsername());
            verify(userRepository).deleteById(user.getUsername());
        }

        @Test
        public void updateUserTest() {
            User theUser = new User();
            theUser.setUsername("Maria");
            theUser.setPassword("password");
            User update = new User();
            update.setUsername("Jose");
            update.setPassword("password");
            when(userRepository.existsById(update.getUsername())).thenReturn(true);
            when(userRepository.findById(update.getUsername())).thenReturn(Optional.of(theUser));
            userServiceImpl.updateUser(update.getUsername(), update);
            verify(userRepository).save(userCaptor.capture());
            User savedUser = userCaptor.getValue();
            assertThat(savedUser.getPassword().equals(update.getPassword()));
        }


        @Test
        public void getAuthorityTest() {
            User testUser = new User();
            testUser.setUsername("username");
            testUser.addAuthority(new Authority(testUser.getUsername(), "ADMIN"));
            when(userRepository.existsById(testUser.getUsername())).thenReturn(true);
            when(userRepository.findById(testUser.getUsername())).thenReturn(java.util.Optional.of(testUser));
            assertEquals(testUser.getAuthorities(), userServiceImpl.getAuthorities(testUser.getUsername()));
        }

        @Test
        public void addAuthorityTest(){
            User testUser = new User();
            testUser.setUsername("username");
            testUser.addAuthority(new Authority(testUser.getUsername(), "USER"));
            when(userRepository.existsById(testUser.getUsername())).thenReturn(true);
            when(userRepository.findById(testUser.getUsername())).thenReturn(java.util.Optional.of(testUser));
            userServiceImpl.addAuthority(testUser.getUsername(), "USER");
            verify(userRepository).save(testUser);
        }

        @Test
        public void removeAuthorityTest() {
            User testUser = new User();
            testUser.setUsername("username");
            testUser.addAuthority(new Authority(testUser.getUsername(), "ADMIN"));
            when(userRepository.existsById(testUser.getUsername())).thenReturn(true);
            when(userRepository.findById(testUser.getUsername())).thenReturn(java.util.Optional.of(testUser));
            userServiceImpl.removeAuthority(testUser.getUsername(), "ADMIN");
            verify(userRepository).save(testUser);
        }



    }

