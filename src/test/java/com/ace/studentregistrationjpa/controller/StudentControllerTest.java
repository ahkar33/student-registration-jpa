package com.ace.studentregistrationjpa.controller;

import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.entity.Student;
import com.ace.studentregistrationjpa.repository.StudentRepository;
import com.ace.studentregistrationjpa.service.CourseService;
import com.ace.studentregistrationjpa.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

// import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @MockBean
    StudentService studentService;

    @MockBean
    StudentRepository repo;

    @Test
    void studentManagementTest() throws Exception {
        this.mockMvc.perform(get("/student/studentManagement"))
                .andExpect(status().isOk())
                .andExpect(view().name("STU003"))
                .andExpect(model().attributeExists("studentList"));
    }

    @Test
    void setupAddStudentTest() throws Exception {
        this.mockMvc.perform(get("/student/addStudent"))
                .andExpect(status().isOk())
                .andExpect(view().name("STU001"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("data"));
    }

    // @Test
    // void addStudentTest() throws Exception {
    //     List<Course> courseList = new ArrayList<>();
    //     courseList.add(new Course("1", "Java"));
    //     Student student = Student.builder()
    //             .id("1")
    //             .name("Joey")
    //             .dob("2000-8-1")
    //             .gender("Male")
    //             .education("IT Diploma")
    //             .attendCourses(courseList)
    //             .phone("343434223")
    //             .build();
    //     List<Student> studentList = new ArrayList<>();
    //     studentList.add(student);
    //     given(studentService.selectAllStudents()).willReturn(studentList);
    //     assertEquals(studentList, studentService.selectAllStudents());
    // }

    @Test
    void deleteStudentTest() throws Exception {
        this.mockMvc.perform(get("/student/deleteStudent/{id}", "STU001"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/student/studentManagement"));
    }

    @Test
    void seeMoreTest() throws Exception {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("1", "Java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCourses(courseList)
                .phone("343434223")
                .build();
        given(studentService.selectStudentById("1")).willReturn(student);
        given(courseService.selectAllCourses()).willReturn(courseList);
        this.mockMvc.perform(get("/student/seeMore/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("STU002"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    void searchStudentTest() throws Exception {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("1", "Java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCourses(courseList)
                .phone("343434223")
                .build();
       List<Student> studentList = new ArrayList<>();
       studentList.add(student);
       given(studentService.selectAllStudents()).willReturn(studentList);
       given(studentService.selectStudentListByIdOrNameOrCourse("1", "Joey", "java"))
               .willReturn(studentList);
       this.mockMvc.perform(get("/student/searchStudent")
               .param("id", "1")
               .param("name", "Joey")
               .param("course", "java"))
               .andExpect(view().name("STU003"))
               .andExpect(model().attributeExists("studentList"));
    }

    @Test
    void searchStudentWhenSearchListIsZeroTest() throws Exception {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("1", "Java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCourses(courseList)
                .phone("343434223")
                .build();
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        List<Student> searchList = new ArrayList<>();
        given(studentService.selectAllStudents()).willReturn(studentList);
        given(studentService.selectStudentListByIdOrNameOrCourse("1", "Joey", "java"))
                .willReturn(searchList);
        this.mockMvc.perform(get("/student/searchStudent")
                        .param("id", "1")
                        .param("name", "Joey")
                        .param("course", "java"))
                .andExpect(view().name("STU003"))
                .andExpect(model().attributeExists("studentList"));
    }

    @Test
    public void addStudentWithoutCourseTest() throws Exception{
//        List<Course> courseList = new ArrayList<>();
        List<String> courseListString = new ArrayList<>();
        List<Course> fetchCourseList = new ArrayList<>();
        fetchCourseList.add(new Course("1", "Java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCoursesString(courseListString)
                .phone("343434223")
                .build();
        given(courseService.selectAllCourses()).willReturn(fetchCourseList);
        this.mockMvc.perform(post("/student/addStudent").flashAttr("data", student))
                .andExpect(view().name("STU001"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    public void blankAddStudentTest() throws Exception{
        List<String> courseListString = new ArrayList<>();
        courseListString.add("1");
        List<Course> fetchCourseList = new ArrayList<>();
        fetchCourseList.add(new Course("1", "Java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("")
                .attendCoursesString(courseListString)
                .phone("343434223")
                .build();
        given(courseService.selectAllCourses()).willReturn(fetchCourseList);
        this.mockMvc.perform(post("/student/addStudent").flashAttr("data", student))
                .andExpect(view().name("STU001"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("data"));
    }
    @Test
    public void ifStudentListIsNullAddStudentTest() throws Exception{
        List<String> courseListString = new ArrayList<>();
        courseListString.add("1");
        List<Course> fetchCourseList = new ArrayList<>();
        fetchCourseList.add(new Course("1", "Java"));
        Student student = Student.builder()
                .id("STU001")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCoursesString(courseListString)
                .phone("343434223")
                .build();
        given(courseService.selectAllCourses()).willReturn(fetchCourseList);
        given(studentService.selectAllStudents()).willReturn(null);
        this.mockMvc.perform(post("/student/addStudent").flashAttr("data", student))
                .andExpect(view().name("STU001"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    public void ifStudentListIsNotNullAddStudentTest() throws Exception{
        List<String> courseListString = new ArrayList<>();
        courseListString.add("1");
        List<Course> fetchCourseList = new ArrayList<>();
        fetchCourseList.add(new Course("1", "java"));
        fetchCourseList.add(new Course("2", "python"));
        Student student = Student.builder()
                .id("STU001")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCoursesString(courseListString)
                .phone("343434223")
                .build();
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        given(courseService.selectAllCourses()).willReturn(fetchCourseList);
        given(studentService.selectAllStudents()).willReturn(studentList);
        this.mockMvc.perform(post("/student/addStudent").flashAttr("data", student))
                .andExpect(view().name("STU001"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    public void updateStudentWithoutCourseTest() throws Exception{
//        List<Course> courseList = new ArrayList<>();
        List<String> courseListString = new ArrayList<>();
        List<Course> fetchCourseList = new ArrayList<>();
        fetchCourseList.add(new Course("1", "Java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCoursesString(courseListString)
                .phone("343434223")
                .build();
        given(courseService.selectAllCourses()).willReturn(fetchCourseList);
        this.mockMvc.perform(post("/student/updateStudent").flashAttr("data", student))
                .andExpect(view().name("STU002"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    public void blankUpdateStudentTest() throws Exception{
        List<String> courseListString = new ArrayList<>();
        courseListString.add("1");
        List<Course> fetchCourseList = new ArrayList<>();
        fetchCourseList.add(new Course("1", "java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("")
                .attendCoursesString(courseListString)
                .phone("343434223")
                .build();
        given(courseService.selectAllCourses()).willReturn(fetchCourseList);
        this.mockMvc.perform(post("/student/updateStudent").flashAttr("data", student))
                .andExpect(view().name("STU002"))
                .andExpect(model().attributeExists("courseList"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("data"));
    }

    @Test
    public void updateStudentTest() throws Exception{
        List<String> courseListString = new ArrayList<>();
        courseListString.add("1");
        List<Course> fetchCourseList = new ArrayList<>();
        fetchCourseList.add(new Course("1", "java"));
        Student student = Student.builder()
                .id("1")
                .name("Joey")
                .dob("2000-8-1")
                .gender("Male")
                .education("IT Diploma")
                .attendCoursesString(courseListString)
                .phone("343434223")
                .build();
        given(courseService.selectAllCourses()).willReturn(fetchCourseList);
        this.mockMvc.perform(post("/student/updateStudent").flashAttr("data", student))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/student/studentManagement"));
    }

}