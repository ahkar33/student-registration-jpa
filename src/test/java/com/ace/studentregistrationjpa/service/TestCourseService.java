package com.ace.studentregistrationjpa.service;

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

import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.repository.CourseRepository;

@SpringBootTest
public class TestCourseService {
    @Mock
    CourseRepository repo;

    @InjectMocks
    CourseService service;

    @Test
    public void checkCourseNameTest(){
        when(repo.existsByName("Java")).thenReturn(true);
        assertTrue(service.checkCourseName("Java"));
        assertFalse(service.checkCourseName("C"));
        verify(repo, times(1)).existsByName("Java");
    }

    @Test
    public void selectAllCoursesTest(){
        List<Course> list = new ArrayList<>();
        Course c1 = Course.builder()
                    .id("COU001")
                    .name("Java")
                    .build();
        Course c2 = Course.builder()
                    .id("COU002")
                    .name("C++")
                    .build();
        Collections.addAll(list, c1, c2);
        when(repo.findAll()).thenReturn(list);
        List<Course> userList = service.selectAllCourses();
        assertEquals(2, userList.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void insertCourseTest(){
        Course c1 = Course.builder()
                    .id("COU001")
                    .name("Java")
                    .build();
        service.insertCourse(c1);
        verify(repo, times(1)).save(c1);
    }

}
