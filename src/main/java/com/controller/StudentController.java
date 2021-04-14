package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model.Student;
import com.repository.StudentRepository;
import com.request.StudentRequest;
import com.response.ErrorResponse;
import com.response.StudentResponse;
import com.service.StudentServiceImp;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
	@Autowired
	StudentServiceImp studentServiceImp;
	@Autowired
	StudentRepository studentRepository;
	// create
	@PostMapping(value = "/create")
	public ResponseEntity<?> createStudent(@RequestBody StudentRequest request) {
		StudentResponse response = new StudentResponse();
		ErrorResponse errorResponse;
		List<ErrorResponse> errors = new ArrayList<ErrorResponse>();

		if (StringUtils.isEmpty(request.getEmail())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("email");
			errorResponse.setMessage("Email is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getFirstName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("firstname");
			errorResponse.setMessage("FirstName is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getLastName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("lastname");
			errorResponse.setMessage("LastName is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (request.getBirthDay() == null) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("birthday");
			errorResponse.setMessage("Birthday is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} else {
			// save user
			Student student = new Student();
			student.setEmail(request.getEmail());
			student.setFirstName(request.getFirstName());
			student.setLastName(request.getLastName());
			student.setBirthDay(request.getBirthDay());
			student.setIsActive("Y");
			student.setIsDeleted("N");
			studentServiceImp.createStudent(student);
			// data reponse
			Student st = studentServiceImp.findByEmail(request.getEmail());
			response.setId(st.getId());
			response.setEmail(st.getEmail());
			response.setFirstName(st.getFirstName());
			response.setLastName(st.getLastName());
			response.setBirthDay(st.getBirthDay());
			response.setIsActive(st.getIsActive());
			response.setIsDeleted(st.getIsDeleted());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
	}
	
	// get 
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getStudent(@PathVariable Long id ){
		StudentResponse response = new StudentResponse();
		Optional<Student> student = studentServiceImp.findById(id);
		if (!student.isPresent()) {
			response.setMessage("User " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			response.setId(student.get().getId());
			response.setEmail(student.get().getEmail());
			response.setFirstName(student.get().getFirstName());
			response.setLastName(student.get().getLastName());
			response.setBirthDay(student.get().getBirthDay());
			response.setIsActive(student.get().getIsActive());
			response.setIsDeleted(student.get().getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	// list
	public ResponseEntity<?> listStudent(){
		StudentResponse response = new StudentResponse();
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			response.setMessage("No user found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(students);
	}
	
	//update 
	@PutMapping(value = "/update")
	public ResponseEntity<?> updateUser(@RequestBody StudentRequest request) {
		StudentResponse response = new StudentResponse();
		ErrorResponse errorResponse;
		ArrayList<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		Student student = studentServiceImp.findByEmail(request.getEmail());

		if (StringUtils.isEmpty(request.getEmail())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("email");
			errorResponse.setMessage("Email is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getFirstName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("firstname");
			errorResponse.setMessage("FirstName is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getLastName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("lastname");
			errorResponse.setMessage("LastName is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}if (request.getBirthDay()==null) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("lastname");
			errorResponse.setMessage("LastName is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (student == null) {
			response.setMessage("Student " + request.getEmail() + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			// save user
			student.setEmail(request.getEmail());
			student.setFirstName(request.getFirstName());
			student.setLastName(request.getLastName());
			request.setBirthDay(request.getBirthDay());
			if (StringUtils.isEmpty(request.getIsActive())) {
				request.setIsActive("Y");
			} else {
				request.setIsActive(request.getIsActive());
			}
			if (StringUtils.isEmpty(request.getIsDeleted())) {
				request.setIsDeleted("N");
			} else {
				request.setIsDeleted(request.getIsDeleted());
			}
			studentServiceImp.updateStudent(student);

			// data reponse
			Student st = studentServiceImp.findByEmail(request.getEmail());
			response.setId(st.getId());
			response.setEmail(st.getEmail());
			response.setFirstName(st.getFirstName());
			response.setLastName(st.getLastName());
			request.setBirthDay(st.getBirthDay());
			response.setIsActive(st.getIsActive());
			response.setIsDeleted(st.getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	// deleted
	@DeleteMapping(value = "/deleted/{id}")
	public ResponseEntity<?> deletedUser(@PathVariable Long id) {
		StudentResponse response = new StudentResponse();
		Optional<Student> student = studentServiceImp.findById(id);
		if (!student.isPresent()) {
			response.setMessage("Student " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			studentServiceImp.deletedStudent(id);
			response.setMessage("Student " + id + " deleted");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	// import csv 
	@PostMapping(value = "/import")
	public ResponseEntity<?> importFromCSV(@RequestBody MultipartFile file) throws IOException {
		StudentResponse response = new StudentResponse();
		List<Student> students = new ArrayList<Student>();
		BufferedReader bufferedReader;
		InputStream inputStream = file.getInputStream();
		Student student;
		String line ;
		if (!file.getOriginalFilename().contains(".csv")) {
			response.setMessage("File is invalid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			line = bufferedReader.readLine();
			while ((line=bufferedReader.readLine()) != null) {
				String [] data = line.split(",");	
					Student st = new Student();
					st.setEmail(data[0]);
					st.setFirstName(data[1]);
					st.setLastName(data[2]);
					st.setBirthDay(Long.parseLong(data[3]));
					st.setIsActive(data[4]);
					st.setIsDeleted(data[5]);
					students.add(st);
			}
		} catch (Exception e) {
		}
		for(Student listDataStudent : students) {
			student = studentRepository.findStudentByEmail(listDataStudent.getEmail());
			if(student != null) {
				continue;
			}else {
				studentServiceImp.createStudent(listDataStudent);
			}
		}
		response.setMessage("Data is imported successfully");
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	
}
