package com.ace.studentregistrationjpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ace.studentregistrationjpa.entity.Student;
import com.ace.studentregistrationjpa.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/studentManagement")
    public String studentManagement(ModelMap model) {
        List<Student> studentList = studentService.selectAllStudents();
        model.addAttribute("studentList", studentList);
        return "STU003";
    }

    @GetMapping("/searchStudent")
    public String searchStudent(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("course") String course, ModelMap model){
    	List<Student> list = studentService.selectAllStudents();
    	List<Student> studentList = new ArrayList<>();
    	studentList = studentService.selectStudentListByIdOrNameOrCourse(list, id, name, course);
        if(studentList.size() == 0) {
        	model.addAttribute("studentList", list);
        	return "STU003";
        }
        model.addAttribute("studentList", studentList);
    	return "STU003";
    }

}
