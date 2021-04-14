package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Student;
import com.repository.StudentRepository;

@Service
public class StudentServiceImp implements StudentService {
	@Autowired
	StudentRepository studentRepository;

	@Override
	public Optional<Student> findById(long id) {
		return studentRepository.findById(id);
	}

	@Override
	public Student findByEmail(String email) {
		return studentRepository.findStudentByEmail(email);
	}

	@Override
	public void createStudent(Student student) {
		studentRepository.save(student);
	}

	@Override
	public void updateStudent(Student student) {
		studentRepository.save(student);
	}

	@Override
	public void deletedStudent(long id) {
		Student student = studentRepository.findStudentById(id);
		student.setIsDeleted("Y");
		studentRepository.save(student);
	}

}
