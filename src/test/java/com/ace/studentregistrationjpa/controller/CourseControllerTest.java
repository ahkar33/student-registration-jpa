package com.ace.studentregistrationjpa.controller;
import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.repository.CourseRepository;
import com.ace.studentregistrationjpa.service.CourseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;

// import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void emptyAddCourseTest() throws Exception {
        Course courseBean = Course.builder().id("1").name("").build();
        this.mockMvc.perform(post("/course/addCourse").flashAttr("data", courseBean))
                .andExpect(status().isOk())
                .andExpect(view().name("BUD003"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    void sameNameAddCourseTest() throws Exception {
        Course courseBean = Course.builder().id("1").name("java").build();
        given(courseService.checkCourseName(courseBean.getName())).willReturn(true);
        this.mockMvc.perform(post("/course/addCourse").flashAttr("data", courseBean))
                .andExpect(status().isOk())
                .andExpect(view().name("BUD003"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    void courseListSizeNotZeroTestAddCourseTest() throws Exception{
        Course courseBean = Course.builder().id("COU001").name("java").build();
        List<Course> courseList = new ArrayList<>();
        Course course = Course.builder().id("COU001").name("java").build();
        courseList.add(course);
        given(courseService.selectAllCourses()).willReturn(courseList);
        this.mockMvc.perform(post("/course/addCourse").flashAttr("data", courseBean))
                .andExpect(status().isOk())
                .andExpect(view().name("BUD003"))
                .andExpect(model().attributeExists("data"))
                .andExpect(model().attributeExists("message"));
    }
    @Test
    void courseListSizeZeroTestAddCourseTest() throws Exception{
        Course courseBean = Course.builder().id("COU001").name("java").build();
        List<Course> courseList = new ArrayList<>();
        given(courseService.selectAllCourses()).willReturn(courseList);
        this.mockMvc.perform(post("/course/addCourse").flashAttr("data", courseBean))
                .andExpect(status().isOk())
                .andExpect(view().name("BUD003"))
                .andExpect(model().attributeExists("data"))
                .andExpect(model().attributeExists("message"));
    }

}