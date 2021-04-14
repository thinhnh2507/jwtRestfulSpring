package com.service;

import java.util.Optional;

import com.model.Permission;

public interface PermissionService {
	Optional<Permission> findById(long id);

	Permission findByName(String name);

	void createPermission(Permission permission);

	void updatePermission(Permission permission);

	void deletedPermission(Long id);
}
