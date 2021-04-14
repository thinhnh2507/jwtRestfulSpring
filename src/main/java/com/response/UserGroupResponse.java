package com.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.model.UserGroup;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroupResponse {
	private String message;
	private List<UserGroup> mapping;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<UserGroup> getMapping() {
		return mapping;
	}

	public void setMapping(List<UserGroup> mapping) {
		this.mapping = mapping;
	}

}
