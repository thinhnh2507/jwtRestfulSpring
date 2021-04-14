package com.request;

public class PermissionRequest {
	private String name;
	private String uri;
	private String description;
	private String isDeleted;

	public void setName(String name) {
		this.name = name;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getName() {
		return name;
	}

	public String getUri() {
		return uri;
	}

	public String getDescription() {
		return description;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

}
