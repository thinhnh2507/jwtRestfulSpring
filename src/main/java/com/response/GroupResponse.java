package com.response;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.model.Group;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupResponse {
private Long id ; 
private String name ; 
private String description;
private String isDeleted;
private String message;
private List<ErrorResponse> errors;

//	Test
//private Optional<Group> group;
//public Optional<Group> getGroup() {
//	return group;
//}
//public void setGroup(Optional<Group> group2) {
//	this.group = group2;
//}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
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
