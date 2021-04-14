package com.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private Long birthDay;
	private String isActive;
	private String isDeleted;
	private String message;
	private List<ErrorResponse> errors;

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

	public Long getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Long birthDay) {
		this.birthDay = birthDay;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorResponse> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorResponse> errors) {
		this.errors = errors;
	}

}
