package com.ace.studentregistrationjpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.studentregistrationjpa.entity.User;
import com.ace.studentregistrationjpa.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> test() {
        return userService.selectUserListByIdOrName("USR001", "admin");
    }

    @GetMapping("/findAllUsers")
    public List<User> findAllUsers() {
        return userService.selectAllUsers();
    }

}
