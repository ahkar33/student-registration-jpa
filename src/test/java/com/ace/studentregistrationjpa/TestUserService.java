package com.ace.studentregistrationjpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.studentregistrationjpa.entity.User;
import com.ace.studentregistrationjpa.repository.UserRepository;
import com.ace.studentregistrationjpa.service.UserService;

@SpringBootTest
public class TestUserService {

    @Mock
    UserRepository repo;

    @InjectMocks
    UserService service;

    @Test
    public void checkLoginTest(){
        when(repo.existsByEmailAndPassword("joey@gmail.com", "asdf")).thenReturn(true);
        assertTrue(service.checkLogin("joey@gmail.com", "asdf"));
        assertFalse(service.checkLogin("joey@gmail.com", "qwer"));
        verify(repo, times(1)).existsByEmailAndPassword("joey@gmail.com", "asdf");
    }

    @Test
    public void selectAllUsersTest() {
        List<User> list = new ArrayList<>();
        User user1 = User.builder()
                .id("USR001")
                .email("joey@gmail.com")
                .name("Joey")
                .password("asdf")
                .userRole("User")
                .build();
        User user2 = User.builder()
                .id("USR002")
                .email("sheldon@gmail.com")
                .name("Sheldon")
                .password("asdf")
                .userRole("User")
                .build();
        User user3 = User.builder()
                .id("USR003")
                .email("penny@gmail.com")
                .name("Penny")
                .password("asdf")
                .userRole("User")
                .build();
        Collections.addAll(list, user1, user2, user3);
        when(repo.findAll()).thenReturn(list);
        List<User> userList = service.selectAllUsers();
        assertEquals(3, userList.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void selectByUserIdTest(){
        User setUser = User.builder()
                .id("USR001")
                .email("joey@gmail.com")
                .name("Joey")
                .password("asdf")
                .userRole("User")
                .build();
        when(repo.findByUserId("USR001")).thenReturn(setUser);   
        User getUser = service.selectUserById("USR001");
        assertEquals("joey@gmail.com", getUser.getEmail());
        assertEquals("Joey", getUser.getName());
        assertEquals("asdf", getUser.getPassword());
        assertEquals("User", getUser.getUserRole());
        verify(repo, times(1)).findByUserId("USR001");
    }

    @Test
    public void selectByUserEmailTest(){
        User setUser = User.builder()
                .id("USR001")
                .email("joey@gmail.com")
                .name("Joey")
                .password("asdf")
                .userRole("User")
                .build();
        when(repo.findByEmail("joey@gmail.com")).thenReturn(setUser);   
        User getUser = service.selectUserByEmail("joey@gmail.com");
        assertEquals("USR001", getUser.getId());
        assertEquals("joey@gmail.com", getUser.getEmail());
        assertEquals("Joey", getUser.getName());
        assertEquals("asdf", getUser.getPassword());
        assertEquals("User", getUser.getUserRole());
        verify(repo, times(1)).findByEmail("joey@gmail.com");
    }

    @Test
    public void checkEmailExistsTest(){
        User setUser = User.builder()
                .id("USR001")
                .email("joey@gmail.com")
                .name("Joey")
                .password("asdf")
                .userRole("User")
                .build();
        when(repo.findByEmail("joey@gmail.com")).thenReturn(setUser);   
        assertTrue(service.checkEmailExists("joey@gmail.com"));
        assertFalse(service.checkEmailExists("penny@gmail.com"));
        verify(repo, times(1)).findByEmail("joey@gmail.com");
    }

    @Test
    public void insertUserTest(){
        User setUser = User.builder()
                .id("USR001")
                .email("joey@gmail.com")
                .name("Joey")
                .password("asdf")
                .userRole("User")
                .build();
        service.insertUser(setUser);
        verify(repo, times(1)).save(setUser);
    }

    @Test
    public void updateUserTest(){
        User setUser = User.builder()
                .id("USR001")
                .email("joey@gmail.com")
                .name("Joey")
                .password("asdf")
                .userRole("User")
                .build();
        service.updateUser(setUser);
        verify(repo, times(1)).updateUser("joey@gmail.com", "Joey", "asdf", "User", "USR001");
    }

    @Test
    public void deleteByIdTest(){
        service.deleteUserById("USR001");
        verify(repo, times(1)).deleteById("USR001");
    }


    @Test
    public void selectUserListByIdOrNameTest(){
        List<User> list = new ArrayList<>();
        User user1 = User.builder()
                .id("USR001")
                .email("joey@gmail.com")
                .name("Joey")
                .password("asdf")
                .userRole("User")
                .build();
        User user2 = User.builder()
                .id("USR002")
                .email("sheldon@gmail.com")
                .name("Sheldon")
                .password("asdf")
                .userRole("User")
                .build();
        Collections.addAll(list, user1, user2);
        when(repo.findByIdContainingOrNameContaining("USR001", "Sheldon")).thenReturn(list);
        List<User> userList = service.selectUserListByIdOrName("USR001", "Sheldon");
        assertEquals(2, userList.size());
        verify(repo, times(1)).findByIdContainingOrNameContaining("USR001", "Sheldon");
    }
}
