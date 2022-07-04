package com.ace.studentregistrationjpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ace.studentregistrationjpa.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findDistinctByIdContainingOrNameContainingOrAttendCourses_NameContaining(
            String id, String name, String course);

    @Modifying
    @Transactional
    @Query(value = "delete from student s where s.id = ?1", nativeQuery = true)
    void deleteStudentById(String id);

    @Modifying
    @Query(value = "delete from student_course sc where sc.student_id = ?1", nativeQuery = true)
    void deleteCoursesByStudentId(String id);

}
