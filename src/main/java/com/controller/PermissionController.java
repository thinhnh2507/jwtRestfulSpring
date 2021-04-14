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

import com.model.Permission;
import com.repository.PermissionRepository;
import com.request.PermissionRequest;
import com.response.ErrorResponse;
import com.response.PermissionResponse;
import com.service.PermissionServiceImp;

@RestController
@RequestMapping(value ="/permission")
public class PermissionController {
	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	PermissionServiceImp permissionServiceImp;

	// create
	@PostMapping(value = "/create")
	public ResponseEntity<?> createPermission(@RequestBody PermissionRequest request) {
		PermissionResponse response = new PermissionResponse();
		ErrorResponse errorResponse ;
		List<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		
		if (StringUtils.isEmpty(request.getName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("Name");
			errorResponse.setMessage("Name is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getUri())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("Uri");
			errorResponse.setMessage("Uri is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getDescription())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("Description");
			errorResponse.setMessage("Description is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			
		}else {
			Permission permission = new Permission();
			permission.setName(request.getName());
			permission.setUri(request.getUri());
			permission.setDescription(request.getDescription());
			permission.setIsDeleted("N");
			permissionServiceImp.createPermission(permission);
			// reponse
			Permission per = permissionServiceImp.findByName(request.getName());
			response.setId(per.getId());
			response.setName(per.getName());
			response.setUri(per.getUri());
			response.setDescription(per.getDescription());
			response.setIsDeleted(per.getIsDeleted());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
	}

	// get by id
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getPermission(@PathVariable long id) {
		PermissionResponse response = new PermissionResponse();
		Optional<Permission> permission = permissionServiceImp.findById(id);
		if (!permission.isPresent()) {
			response.setMessage("User " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			response.setId(permission.get().getId());
			response.setName(permission.get().getName());
			response.setUri(permission.get().getUri());
			response.setDescription(permission.get().getDescription());
			response.setIsDeleted(permission.get().getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	// list
	@GetMapping(value = "/list")
	public ResponseEntity<?> getListPermission() {
		PermissionResponse response = new PermissionResponse();
		List<Permission> listUser = permissionRepository.findAll();
		if (listUser.isEmpty()) {
			response.setMessage("No user found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listUser);
	}

	// update
	@PutMapping(value = "/update")
	public ResponseEntity<?> updatePermission(@RequestBody PermissionRequest request) {
		PermissionResponse response = new PermissionResponse();
		ErrorResponse errorResponse ;
		ArrayList<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		Permission permission = permissionServiceImp.findByName(request.getName());

		if (StringUtils.isEmpty(request.getName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("Name");
			errorResponse.setMessage("Name is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("Uri");
			errorResponse.setMessage("Name is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("Description");
			errorResponse.setMessage("Name is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (permission == null) {
			response.setMessage("User " + request.getName() + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			// save user
			permission.setName(request.getName());
			permission.setUri(request.getUri());
			permission.setDescription(request.getDescription());
			if (StringUtils.isEmpty(request.getIsDeleted())) {
				permission.setIsDeleted("N");
			} else {
				permission.setIsDeleted(request.getIsDeleted());
			}
			permissionServiceImp.updatePermission(permission);

			// data reponse
			Permission per = permissionServiceImp.findByName(request.getName());
			response.setId(per.getId());
			response.setName(per.getName());
			response.setUri(per.getUri());
			response.setDescription(per.getDescription());
			response.setIsDeleted(per.getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	// deleted
	@DeleteMapping(value = "/deleted/{id}")
	public ResponseEntity<?> deletedPermission(@PathVariable Long id) {
		PermissionResponse response = new PermissionResponse();
		Optional<Permission> permission = permissionServiceImp.findById(id);
		if (!permission.isPresent()) {
			response.setMessage("Permission " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			permissionServiceImp.deletedPermission(id);
			response.setMessage("Permission " + id + " deleted");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	
	// import csv 
	@PostMapping(value = "/import")
	public ResponseEntity<?> importFromCSV(@RequestBody MultipartFile file) throws IOException {
		PermissionResponse response = new PermissionResponse();
		List<Permission> permissions = new ArrayList<Permission>();
		BufferedReader bufferedReader;
		InputStream inputStream = file.getInputStream();
		Permission permission;
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
					Permission per = new Permission();
					per.setName(data[0]);
					per.setUri(data[1]);
					per.setDescription(data[2]);
					per.setIsDeleted(data[3]);
					permissions.add(per);
			}
		} catch (Exception e) {
		}
		for(Permission listDataPer : permissions) {
			permission = permissionServiceImp.findByName(listDataPer.getName());
			if(permission != null) {
				continue;
			}else {
				permissionServiceImp.createPermission(permission);
			}
		}
		response.setMessage("Data is imported successfully");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
