package com.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.model.GroupPermission;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupPerResponse {
	private String message;
	private List<GroupPermission> mapping;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<GroupPermission> getMapping() {
		return mapping;
	}

	public void setMapping(List<GroupPermission> mapping) {
		this.mapping = mapping;
	}

}
