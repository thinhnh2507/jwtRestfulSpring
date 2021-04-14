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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.config.CustomUserDetail;
import com.jwt.JwtTokenProvider;
import com.model.User;
import com.repository.UserRepository;
import com.request.JwtRequest;
import com.response.ErrorResponse;
import com.response.JwtResponse;
import com.service.UserServiceImp;



@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	UserServiceImp userServiceImp;
	@Autowired
	PasswordEncoder passwordEncoder;
	// login
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody JwtRequest request) {

		Authentication authentication;
		JwtResponse response = new JwtResponse();
		ErrorResponse errorResponse;
		ArrayList<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		if (StringUtils.isEmpty(request.getEmail())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("email");
			errorResponse.setMessage("Email is required");
			errors.add(errorResponse);
		}
		if (StringUtils.isEmpty(request.getPassword())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("password");
			errorResponse.setMessage("Password is required");
			errors.add(errorResponse);
		}
		if (StringUtils.isEmpty(request.getEmail()) || StringUtils.isEmpty(request.getPassword())) {
			response.setErrors(errors);
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		} catch (AuthenticationException e) {
			response.setMessage("Email or password does not match");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
		response.setType("Bearer");
		response.setTokken(jwt);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// create
	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@RequestBody JwtRequest request) {
		JwtResponse response = new JwtResponse();
		ErrorResponse errorResponse;
		List<ErrorResponse> errors = new ArrayList<ErrorResponse>();

		if (StringUtils.isEmpty(request.getEmail())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("email");
			errorResponse.setMessage("Email is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getPassword())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("password");
			errorResponse.setMessage("Password is required");
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
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		} else {
			// save user
			User createUser = new User();
			createUser.setEmail(request.getEmail());
			createUser.setPassword(passwordEncoder.encode(request.getPassword()));
			createUser.setFirstName(request.getFirstName());
			createUser.setLastName(request.getLastName());
			createUser.setIsActive("Y");
			createUser.setIsDeleted("N");
			userServiceImp.createUser(createUser);
			// data reponse
			User ur = userServiceImp.findByEmail(request.getEmail());
			response.setId(ur.getId());
			response.setEmail(ur.getEmail());
			response.setFirstName(ur.getFirstName());
			response.setLastName(ur.getLastName());
			response.setIsActive(ur.getIsActive());
			response.setIsDeleted(ur.getIsDeleted());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}

	}

	// get by id
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUser(@PathVariable long id) {
		JwtResponse response = new JwtResponse();
		Optional<User> user = userServiceImp.findById(id);

		if (!user.isPresent()) {
			response.setMessage("User " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			response.setId(user.get().getId());
			response.setEmail(user.get().getEmail());
			response.setFirstName(user.get().getFirstName());
			response.setLastName(user.get().getLastName());
			response.setIsActive(user.get().getIsActive());
			response.setIsDeleted(user.get().getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	// update
	@PutMapping(value = "/update")
	public ResponseEntity<?> updateUser(@RequestBody JwtRequest request) {
		JwtResponse response = new JwtResponse();
		ErrorResponse errorResponse;
		ArrayList<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		User user = userServiceImp.findByEmail(request.getEmail());

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
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (user == null) {
			response.setMessage("User " + request.getEmail() + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {

			// save user
			user.setEmail(request.getEmail());
			user.setPassword(request.getPassword());
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			if (StringUtils.isEmpty(request.getIsActive())) {
				user.setIsActive("Y");
			} else {
				user.setIsActive(request.getIsActive());
			}
			if (StringUtils.isEmpty(request.getIsDeleted())) {
				user.setIsDeleted("N");
			} else {
				user.setIsDeleted(request.getIsDeleted());
			}
			userServiceImp.updateUser(user);

			// data reponse
			User ur = userServiceImp.findByEmail(request.getEmail());
			response.setId(ur.getId());
			response.setEmail(ur.getEmail());
			response.setFirstName(ur.getFirstName());
			response.setLastName(ur.getLastName());
			response.setIsActive(ur.getIsActive());
			response.setIsDeleted(ur.getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	// list
	@GetMapping(value = "/list")
	public ResponseEntity<?> getListUser() {
		JwtResponse response = new JwtResponse();
		List<User> listUser = userRepository.findAll();
		if (listUser.isEmpty()) {
			response.setMessage("No user found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listUser);
	}

	// deleted
	@DeleteMapping(value = "/deleted/{id}")
	public ResponseEntity<?> deletedUser(@PathVariable Long id) {
		JwtResponse response = new JwtResponse();
		Optional<User> user = userServiceImp.findById(id);
		if (!user.isPresent()) {
			response.setMessage("User " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			userServiceImp.deletedUser(id);
			response.setMessage("User " + id + " deleted");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	// change password
	@PostMapping(value = "/changepassword")
	public ResponseEntity<?> changePasswrod(@RequestBody JwtRequest request) {
		JwtResponse response = new JwtResponse();
		ErrorResponse errorResponse;
		ArrayList<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		User user = userRepository.findByEmail(request.getEmail());
		System.out.println(user);
		if (StringUtils.isEmpty(request.getEmail())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("email");
			errorResponse.setMessage("Email is required");
			errors.add(errorResponse);
		}
		if (StringUtils.isEmpty(request.getPassword())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("password");
			errorResponse.setMessage("Password is required");
			errors.add(errorResponse);
		}
		if (StringUtils.isEmpty(request.getEmail()) || StringUtils.isEmpty(request.getPassword())) {
			response.setErrors(errors);
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (!(user == null)) {
			response.setMessage("Password is changed successfully");
			user.setPassword(request.getPassword());
			userServiceImp.updateUser(user);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			response.setMessage("Email or password does not match");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	// import
	@PostMapping(value = "/import")
	public ResponseEntity<?> importFromCSV(@RequestBody MultipartFile file) throws IOException {
		JwtResponse response = new JwtResponse();
		List<User> users = new ArrayList<User>();
		BufferedReader bufferedReader;
		InputStream inputStream = file.getInputStream();
		User user;
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
					User us = new User();
					us.setEmail(data[0]);
					us.setPassword(data[1]);
					us.setFirstName(data[2]);
					us.setLastName(data[3]);
					us.setIsActive(data[4]);
					us.setIsDeleted(data[5]);
					users.add(us);
			}
		} catch (Exception e) {
		}
		for(User listDataUser : users) {
			user = userRepository.findByEmail(listDataUser.getEmail());
			if(user != null) {
				continue;
			}else {
				userServiceImp.createUser(listDataUser);
			}
		}
		response.setMessage("Data is imported successfully");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
}
