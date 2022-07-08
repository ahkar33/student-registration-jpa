package com.ace.studentregistrationjpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import com.ace.studentregistrationjpa.entity.User;
import com.ace.studentregistrationjpa.repository.UserRepository;
import com.ace.studentregistrationjpa.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestAuthenticationController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository repo;

    @Test
    public void TestShowLogin() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("LGN001"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    public void TestLoginValidate() throws Exception {
        this.mockMvc.perform(post("/login"))
                .andExpect(status().is(200))
                .andExpect(view().name("LGN001"))
                .andExpect(model().attributeExists("data"))
                .andExpect(model().attributeExists("error"));
    }

    // @Test
    // public void TestLogin() throws Exception {
    //     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //     Date date = new Date(System.currentTimeMillis());
    //     String currentDate = formatter.format(date);
    //     User userBean = User.builder()
    //             .id("USR001")
    //             .name("Admin")
    //             .email("admin@gmail.com")
    //             .password("admin")
    //             .userRole("Admin")
    //             .build();
    //     this.mockMvc.perform(post("/login")
    //             .flashAttr("userBean", userBean)
    //             .sessionAttr("date", currentDate)
    //             .sessionAttr("userInfo", userBean))
    //             .andExpect(status().is(302))
    //             .andExpect(redirectedUrl("/welcome"));
    // }

    @Test
    public void TestLogout() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andExpect(redirectedUrl("/login"));
    }

}
