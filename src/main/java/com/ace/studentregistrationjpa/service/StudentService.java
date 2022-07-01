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

	public List<Student> selectStudentListByIdOrNameOrCourse(String id, String name, String course) {
		// return studentRepository.findByIdOrNameOrCourse("%" + id + "%", "%" + name + "%", "%" + course + "%");
		return studentRepository.findByIdOrNameOrCourse(id, name, course);
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

	// public void updateStudent(Student student) {
	// studentRepository.updateStudent(
	// student.getName(), student.getDob(), student.getGender(),
	// student.getPhone(), student.getEducation(), student.getId());
	// studentRepository.deleteCoursesByStudentId(student.getId());
	// }

	// public List<Course> selectCoursesByStudentId(String id) {
	// return courseRepository.findCoursesByStudentId(id);
	// }

}
