package com.ace.studentregistrationjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentregistrationjpa.entity.Student;
import com.ace.studentregistrationjpa.repository.CourseRepository;
import com.ace.studentregistrationjpa.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	CourseRepository courseRepository;

	public List<Student> selectAllStudents() {
		return studentRepository.findAll();
	}

	public List<Student> selectStudentListByIdOrNameOrCourse(String searchId, String searchName, String searchCourse) {
		// "~" <- this is just random bullshit to avoid sql wildcard, not REGEX
		String id = searchId.isBlank() ? "~" : searchId;
		String name = searchName.isBlank() ? "~" : searchName;
		String course = searchCourse.isBlank() ? "~" : searchCourse;
		return studentRepository.findDistinctByIdContainingOrNameContainingOrAttendCourses_NameContaining(id, name,
				course);
	}

	public void insertStudent(Student student) {
		studentRepository.save(student);
	}

	public Student selectStudentById(String id) {
		return studentRepository.findById(id).get();
	}

	public void deleteStudentById(String id) {
		studentRepository.deleteStudentById(id);
	}

}
