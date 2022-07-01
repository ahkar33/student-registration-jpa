package com.ace.studentregistrationjpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ace.studentregistrationjpa.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

    @Query(value = "select distinct s.id, s.name, s.dob, s.gender, s.phone, s.education from student_course sc inner join student s on sc.student_id = s.id inner join course c on sc.course_id = c.id where s.id like %?1% or s.name like %?2% or c.name like %?3%", nativeQuery = true)
    List<Student> findByIdOrNameOrCourse(String id, String name, String course);

    @Modifying
    @Transactional
    @Query(value = "delete from student s where s.id = ?1", nativeQuery = true)
    void deleteStudentById(String id);

    @Modifying
    @Query(value = "delete from student_course sc where sc.student_id = ?1", nativeQuery = true)
    void deleteCoursesByStudentId(String id);

    // @Modifying
    // @Transactional
    // @Query("update Student s set s.name=?1, s.dob= ?2, s.gender=?3, s.phone=?4,
    // s.education=?5 where s.id=?5")
    // void updateStudent(String name, String dob, String gender, String phone,
    // String education, String id);

}
