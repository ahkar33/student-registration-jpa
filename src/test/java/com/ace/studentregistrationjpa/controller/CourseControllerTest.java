package com.ace.studentregistrationjpa.controller;
import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.repository.CourseRepository;
import com.ace.studentregistrationjpa.service.CourseService;
import com.ace.studentregistrationjpa.repository.UserRepository;
import com.ace.studentregistrationjpa.service.CourseService;
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
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @MockBean
    CourseRepository repo;
    @Test
    void setupAddCourseTest() throws Exception{
        this.mockMvc.perform(get("/course/addCourse"))
                .andExpect(status().isOk())
                .andExpect(view().name("BUD003"))
                .andExpect(model().attributeExists("data"));
    }
}