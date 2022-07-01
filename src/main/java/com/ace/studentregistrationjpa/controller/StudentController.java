package com.ace.studentregistrationjpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ace.studentregistrationjpa.entity.Student;
import com.ace.studentregistrationjpa.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // @GetMapping("/test")
    // public List<?> test(){
    //     return studentService.selectStudentListByIdOrNameOrCourse("STU001", "Anakin", "java");
    // }

    // @GetMapping("/test")
    // public List<Student> test(){
    //     List<Student> studentList =  studentService.selectStudentListByIdOrNameOrCourse("sdfsdf", "()U)U", "c++");
    //     return studentList;
    // }

    @GetMapping("/studentManagement")
    public String studentManagement(ModelMap model) {
        List<Student> studentList = studentService.selectAllStudents();
        model.addAttribute("studentList", studentList);
        return "STU003";
    }

    // @GetMapping("/searchStudent")
    // public String searchStudent(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("course") String course, ModelMap model){
    // 	List<Student> list = studentService.selectAllStudents();
    // 	List<Student> studentList = new ArrayList<>();
    // 	studentList = studentService.selectStudentListByIdOrNameOrCourse(list, id, name, course);
    //     if(studentList.size() == 0) {
    //     	model.addAttribute("studentList", list);
    //     	return "STU003";
    //     }
    //     model.addAttribute("studentList", studentList);
    // 	return "STU003";
    // }

    @GetMapping("/searchStudent")
	public String searchStudent(@RequestParam("id") String searchId, @RequestParam("name") String searchName,
			@RequestParam("course") String searchCourse, ModelMap model) {
		// ")#<>(}" <- this is just random bullshit to avoid sql wildcard, not REGEX
		String id = searchId.isBlank() ? ")#<>(}" : searchId;
		String name = searchName.isBlank() ? ")#<>(}" : searchName;
		String course = searchCourse.isBlank() ? ")#<>(}" : searchCourse;
		List<Student> studentList = (List<Student>)studentService.selectStudentListByIdOrNameOrCourse(id, name, course);
        System.out.println(studentList);
		if (studentList.size() == 0) {
			studentList = studentService.selectAllStudents();
			model.addAttribute("studentList", studentList);
			return "STU003";
		}
		model.addAttribute("studentList", studentList);
		return "STU003";
	}
}
