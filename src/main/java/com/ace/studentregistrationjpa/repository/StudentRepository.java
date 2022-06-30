package com.ace.studentregistrationjpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.studentregistrationjpa.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
	
	
	
}
