package com.ace.studentregistrationjpa.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ace.studentregistrationjpa.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {	    
    @Query(
        value="select distinct s.id, s.name from student_course sc inner join student s on sc.student_id = s.id inner join course c on sc.course_id = c.id where s.id like ?1 or s.name like ?2 or c.name = ?3", 
        nativeQuery = true)
    List<?> findByIdOrNameOrCourse(String id, String name, String course);
}
