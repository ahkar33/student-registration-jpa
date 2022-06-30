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

    public Boolean checkCourseName(String name){
        if(courseRepository.findByName(name) == null){
            return false;
        }
        return true;
    }

    public List<Course> selectAllCourses(){
        return courseRepository.findAll();
    }

    public void insertCourse(Course course){
        courseRepository.save(course);
    }

    // may be select course by student id is needed 

}
