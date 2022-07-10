package com.ace.studentregistrationjpa.controller;

import com.ace.studentregistrationjpa.repository.StudentRepository;
import com.ace.studentregistrationjpa.repository.UserRepository;
import com.ace.studentregistrationjpa.service.StudentService;
import com.ace.studentregistrationjpa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @MockBean
    StudentRepository repo;

    @Test
    void studentManagementTest() throws Exception {
        this.mockMvc.perform(get("/student/studentManagement"))
                .andExpect(status().isOk())
                .andExpect(view().name("STU003"))
                .andExpect(model().attributeExists("studentList"));
    }

    @Test
    void setupAddStudentTest() throws Exception{
        this.mockMvc.perform(get("/student/addStudent"))
                .andExpect(status().isOk())
                .andExpect(view().name("STU001"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    void deleteStudentTest() throws Exception{
        this.mockMvc.perform(get("/student/deleteStudent/{id}", "STU001"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/student/studentManagement"));
    }

}