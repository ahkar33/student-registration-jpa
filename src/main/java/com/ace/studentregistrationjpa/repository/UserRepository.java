package com.ace.studentregistrationjpa.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ace.studentregistrationjpa.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    Optional<User> findById(String id);

    @Modifying
    @Transactional
    @Query("update User u set u.email = ?1, u.name= ?2, u.password =?3, u.userRole = ?4 where u.id = ?5")
    void updateUser(String email, String name, String password, String role, String id);

    List<User> findByIdContainingOrNameContaining(String id, String name);

}
