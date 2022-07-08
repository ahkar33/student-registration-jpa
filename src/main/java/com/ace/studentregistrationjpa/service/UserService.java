package com.ace.studentregistrationjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentregistrationjpa.entity.User;
import com.ace.studentregistrationjpa.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean checkLogin(String email, String password) {
        return userRepository.existsByEmailAndPassword(email, password);
    }

    public User selectUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User selectUserById(String id) {
        return userRepository.findByUserId(id);
    }

    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }

    public Boolean checkEmailExists(String email) {
        if (userRepository.findByEmail(email) == null) {
            return false;
        }
        return true;
    }

    public void insertUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user.getEmail(), user.getName(), user.getPassword(), user.getUserRole(),
                user.getId());
    }

    public List<User> selectUserListByIdOrName(String id, String name) {
        return userRepository.findByIdContainingOrNameContaining(id, name);
    }
    
}
