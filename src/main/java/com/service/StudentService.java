package com.service;

import java.util.Optional;

import com.model.Student;

public interface StudentService {
	Optional<Student> findById(long id);

	Student findByEmail(String email);

	void createStudent(Student student);

	void updateStudent(Student student);

	void deletedStudent(long id);
}
