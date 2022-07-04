package com.ace.studentregistrationjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Boolean checkCourseName(String name) {
        return courseRepository.existsByName(name);
    }

    public List<Course> selectAllCourses() {
        return courseRepository.findAll();
    }

    public void insertCourse(Course course) {
        courseRepository.save(course);
    }

}
