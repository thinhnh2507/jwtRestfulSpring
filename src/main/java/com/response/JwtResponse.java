package com.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.model.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
	private String tokken;
	private String type ;
	private String message;
	private List<ErrorResponse> errors;
	private User user;
	private Long id ; 
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String isActive;
	private String isDeleted;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTokken() {
		return tokken;
	}

	public void setTokken(String tokken) {
		this.tokken = tokken;
	}

	public List<ErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorResponse> errors) {
		this.errors = errors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	

}
