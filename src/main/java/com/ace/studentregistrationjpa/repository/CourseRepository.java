package com.ace.studentregistrationjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.studentregistrationjpa.entity.Course;

public interface CourseRepository extends JpaRepository<Course, String>{
   
    Course findByName(String name);    

    // @Query(
    //     value = "select c.id, c.name from student_course sc inner join course c on sc.course_id = c.id where sc.student_id = ?1",
    //     nativeQuery = true
    // )
    @Query(
        value = "select c.id,c.name from student_course sc inner join course c on sc.course_id = c.id join student s on cs.student_id = s.id where s.id = ?1",
        nativeQuery = true
    )
    String test(String id);

}
