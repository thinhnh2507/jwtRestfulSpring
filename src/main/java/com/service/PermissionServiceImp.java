package com.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.model.Permission;
import com.repository.PermissionRepository;

@Service
public class PermissionServiceImp implements PermissionService {
	@Autowired
	PermissionRepository permissionRepository;

	@Override
	public Optional<Permission> findById(long id) {
		return permissionRepository.findById(id);
	}

	@Override
	public Permission findByName(String name) {
		return permissionRepository.findPermissionByName(name);
	}

	@Override
	public void createPermission(Permission permission) {
		permissionRepository.save(permission);
	}

	@Override
	public void updatePermission(Permission permission) {
		permissionRepository.save(permission);
	}

	@Override
	public void deletedPermission(Long id) {
		Permission per = permissionRepository.findPermissionById(id);
		per.setIsDeleted("Y");
		permissionRepository.save(per);
	}

}
