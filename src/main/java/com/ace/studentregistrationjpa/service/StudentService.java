package com.ace.studentregistrationjpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.entity.Student;
import com.ace.studentregistrationjpa.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
   
    public List<Student> selectAllStudents(){
    	return studentRepository.findAll();
    }
   
    public List<Student> selectStudentListByIdOrNameOrCourse(List<Student> studentList, String searchId, String searchName, String searchCourse){
		List<Student> filterList = new ArrayList<>();
		String name = searchName.isBlank() ? "*%^+<>%(>-=" : searchName;
		String  id = searchId.isBlank() ? "*%^+<>%(>-=" : searchId;
		String course = searchCourse.isBlank() ? "*%^+<>%(>-=" : searchCourse;
		for (Student student : studentList) {
			List<String> attendCourses = new ArrayList<>();
			for(Course c : student.getAttendCourses()) {
				attendCourses.add(c.getName().replaceAll("\\s+", "").toLowerCase());
			}
			if (student.getName().replaceAll("\\s+", "").toLowerCase()
					.contains(name.replaceAll("\\s+", "").toLowerCase())
					|| student.getId().toLowerCase().contains(id.toLowerCase()) || attendCourses.contains(course.replaceAll("\\s+", "").toLowerCase())) {
				filterList.add(student);
			}
		}
		return filterList;
    }

}
