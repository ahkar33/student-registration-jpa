package com.ace.studentregistrationjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.studentregistrationjpa.entity.Course;

public interface CourseRepository extends JpaRepository<Course, String>{
   
    Course findByName(String name);    

}
