package com.ace.studentregistrationjpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.ace.studentregistrationjpa.repository.UserRepository;
import com.ace.studentregistrationjpa.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {
    
    @Autowired    
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository repo;

    @Test
    public void testShowUserManagement() throws Exception{
        this.mockMvc.perform(get("/user/userManagement"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("userList"));
    }
}
