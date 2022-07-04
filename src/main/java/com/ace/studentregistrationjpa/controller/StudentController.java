package com.ace.studentregistrationjpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.entity.Student;
import com.ace.studentregistrationjpa.service.CourseService;
import com.ace.studentregistrationjpa.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/studentManagement")
    public String studentManagement(ModelMap model) {
        List<Student> studentList = studentService.selectAllStudents();
        model.addAttribute("studentList", studentList);
        return "STU003";
    }

    @GetMapping("/addStudent")
    public String setupAddStudent(ModelMap model) {
        List<Course> courseList = courseService.selectAllCourses();
        model.addAttribute("courseList", courseList);
        model.addAttribute("data", new Student());
        return "STU001";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("data") Student studentBean, ModelMap model) {
        List<Course> courseList = courseService.selectAllCourses();
        model.addAttribute("courseList", courseList);
        if (studentBean.getAttendCoursesString().size() == 0) {
            model.addAttribute("error", "Fill the blank !!");
            model.addAttribute("data", studentBean);
            return "STU001";
        }
        if (studentBean.getName().isBlank() || studentBean.getDob().isBlank() || studentBean.getGender().isBlank()
                || studentBean.getPhone().isBlank() || studentBean.getEducation().isBlank()) {
            model.addAttribute("error", "Fill the blank !!");
            model.addAttribute("data", studentBean);
            return "STU001";
        }
        List<Student> studentList = studentService.selectAllStudents();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }
        if (studentList.size() == 0) {
            studentBean.setId("STU001");
        } else {
            int tempId = Integer.parseInt(studentList.get(studentList.size() - 1).getId().substring(3)) + 1;
            String userId = String.format("STU%03d", tempId);
            studentBean.setId(userId);
        }
        for (int i = 0; i < courseList.size(); i++) {
            for (int j = 0; j < studentBean.getAttendCoursesString().size(); j++) {
                if (courseList.get(i).getId().equals(studentBean.getAttendCoursesString().get(j))) {
                    studentBean.addAttendCourses(courseList.get(i));
                }
            }
        }
        studentService.insertStudent(studentBean);
        model.addAttribute("message", "Registered Succesfully !!");
        // clear the bean
        model.addAttribute("data", new Student());
        return "STU001";
    }

    @GetMapping("/searchStudent")
    public String searchStudent(@RequestParam("id") String id, @RequestParam("name") String name,
            @RequestParam("course") String course, ModelMap model) {
        List<Student> studentList = (List<Student>) studentService.selectStudentListByIdOrNameOrCourse(id, name,
                course);
        if (studentList.size() == 0) {
            studentList = studentService.selectAllStudents();
            model.addAttribute("studentList", studentList);
            return "STU003";
        }
        model.addAttribute("studentList", studentList);
        return "STU003";
    }

    @GetMapping("/seeMore/{id}")
    public String seeMore(@PathVariable("id") String id, ModelMap model) {
        Student student = studentService.selectStudentById(id);
        List<Course> courseList = courseService.selectAllCourses();
        for (int i = 0; i < courseList.size(); i++) {
            for (int j = 0; j < student.getAttendCourses().size(); j++) {
                if (courseList.get(i).getId().equals(student.getAttendCourses().get(j).getId())) {
                    student.addAttendCoursesString(courseList.get(i));
                }
            }
        }
        model.addAttribute("courseList", courseList);
        model.addAttribute("data", student);
        return "STU002";
    }

    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("data") Student studentBean, ModelMap model) {
        List<Course> courseList = courseService.selectAllCourses();
        model.addAttribute("courseList", courseList);
        if (studentBean.getAttendCoursesString().size() == 0) {
            model.addAttribute("error", "Fill the blank !!");
            model.addAttribute("data", studentBean);
            return "STU002";
        }
        if (studentBean.getName().isBlank() || studentBean.getDob().isBlank() || studentBean.getGender().isBlank()
                || studentBean.getPhone().length() < 4 || studentBean.getEducation().isBlank()) {
            model.addAttribute("error", "Fill the blank !!");
            model.addAttribute("data", studentBean);
            return "STU002";
        }
        studentService.deleteStudentById(studentBean.getId());
        for (int i = 0; i < courseList.size(); i++) {
            for (int j = 0; j < studentBean.getAttendCoursesString().size(); j++) {
                if (courseList.get(i).getId().equals(studentBean.getAttendCoursesString().get(j))) {
                    studentBean.addAttendCourses(courseList.get(i));
                }
            }
        }
        studentService.insertStudent(studentBean);
        return "redirect:/student/studentManagement";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") String id) {
        studentService.deleteStudentById(id);
        return "redirect:/student/studentManagement";
    }
}
