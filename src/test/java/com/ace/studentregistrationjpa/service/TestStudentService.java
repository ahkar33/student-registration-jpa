package com.ace.studentregistrationjpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.studentregistrationjpa.entity.Student;
import com.ace.studentregistrationjpa.repository.StudentRepository;

@SpringBootTest
public class TestStudentService {
    @Mock
    StudentRepository repo;

    @InjectMocks
    StudentService service;

    static List<Student> list = new ArrayList<>();

    @BeforeAll
    static void init() {
        Student s1 = Student.builder()
                .id("STU001")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .phone("343434223a")
                .build();
        Student s2 = Student.builder()
                .id("STU002")
                .name("Sheldon")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .phone("343434223a")
                .build();
        Collections.addAll(list, s1, s2);
    }

    @Test
    public void selectAllStudentsTest() {
        when(repo.findAll()).thenReturn(list);
        List<Student> studentList = service.selectAllStudents();
        assertEquals(2, studentList.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void selectStudentListByIdOrNameOrCourseTest() {
        when(repo.findDistinctByIdContainingOrNameContainingOrAttendCourses_NameContaining("STU001", "Joey", "Java"))
                .thenReturn(list);
        List<Student> studentList = service.selectStudentListByIdOrNameOrCourse("STU001", "Joey", "Java");
        assertEquals(2, studentList.size());
        verify(repo, times(1))
                .findDistinctByIdContainingOrNameContainingOrAttendCourses_NameContaining("STU001", "Joey", "Java");
    }

    @Test
    public void insertUserTest() {
        Student setStudent = Student.builder()
                .id("STU001")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .phone("343434223a")
                .build();
        service.insertStudent(setStudent);
        verify(repo, times(1)).save(setStudent);
    }

    @Test
    public void deleteByIdTest(){
        service.deleteStudentById("STU001");
        verify(repo, times(1)).deleteStudentById("STU001");
    }

    @Test
    public void selectStudentByIdTest(){
        Student setStudent = Student.builder()
                .id("STU001")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .phone("343434223")
                .build();
        when(repo.findById("STU001")).thenReturn(Optional.ofNullable(setStudent));
        Student getStudent = service.selectStudentById("STU001");
        assertEquals("STU001", getStudent.getId());
        assertEquals("Joey", getStudent.getName());
        assertEquals("2000-8-1", getStudent.getDob());
        assertEquals("Male", getStudent.getGender());
        assertEquals("IT Diploma", getStudent.getEducation());
        assertEquals("343434223", getStudent.getPhone());
        verify(repo, times(1)).findById("STU001");
    }
}
