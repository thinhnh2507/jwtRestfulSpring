package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.model.Group;
import com.model.GroupPermission;
import com.model.Permission;
import com.model.User;
import com.model.UserGroup;
import com.repository.GroupRepository;
import com.repository.PermissionRepository;
import com.repository.UserRepository;
import com.request.GroupPerRequest;
import com.request.GroupRequest;
import com.request.UserGroupRequest;
import com.response.ErrorResponse;
import com.response.GroupPerResponse;
import com.response.GroupResponse;
import com.response.UserGroupResponse;
import com.service.GroupServiceImp;
import com.service.UserServiceImp;

@RestController
@RequestMapping(value = "/group")
public class GroupController {
	@Autowired
	GroupServiceImp groupServiceImp;
	@Autowired
	UserServiceImp userServiceImp;
	@Autowired
	UserRepository userRepository;
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	PermissionRepository permissionRepo;

	// create
	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@RequestBody GroupRequest request) {
		GroupResponse response = new GroupResponse();
		ErrorResponse errorResponse;
		ArrayList<ErrorResponse> errors = new ArrayList<ErrorResponse>();

		if (StringUtils.isEmpty(request.getName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("name");
			errorResponse.setMessage("Name is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (StringUtils.isEmpty(request.getDescription())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("description");
			errorResponse.setMessage("Description is required");
			errors.add(errorResponse);
			response.setErrors(errors);
		}
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} else {
			Group group = new Group();
			group.setName(request.getName());
			group.setDescription(request.getDescription());
			group.setIsDeleted("N");
			groupServiceImp.createGroup(group);

			Group gr = groupServiceImp.findByName(request.getName());
			response.setId(gr.getId());
			response.setName(gr.getName());
			response.setDescription(gr.getDescription());
			response.setIsDeleted(gr.getIsDeleted());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
	}

	// get by id
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getGroupById(@PathVariable Long id) {
		GroupResponse response = new GroupResponse();
		Optional<Group> group = groupServiceImp.findById(id);
		System.out.println(group.get().getId() + "---" + group.get().getDescription());
		if (!group.isPresent()) {
			response.setMessage("Group " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
//			response.setGroup(group);
			response.setId(group.get().getId());
			response.setName(group.get().getName());
			response.setDescription(group.get().getDescription());
			response.setIsDeleted(group.get().getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	// update
	@PutMapping(value = "/update")
	public ResponseEntity<?> updateGroup(@RequestBody GroupRequest request) {
		GroupResponse response = new GroupResponse();
		Group group = groupServiceImp.findByName(request.getName());
		ErrorResponse errorResponse;
		ArrayList<ErrorResponse> errors = new ArrayList<ErrorResponse>();
		if (StringUtils.isEmpty(request.getName())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("name");
			errorResponse.setMessage("Name is required");
			errors.add(errorResponse);
		}
		if (StringUtils.isEmpty(request.getDescription())) {
			errorResponse = new ErrorResponse();
			errorResponse.setField("description");
			errorResponse.setMessage("Description is required");
			errors.add(errorResponse);
		}
		if (!errors.isEmpty()) {
			response.setMessage("Validation error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (group == null) {
			response.setMessage("Group " + request.getName() + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			// update
			group.setName(request.getName());
			group.setDescription(request.getDescription());
			if (StringUtils.isEmpty(request.getIsDeleted())) {
				group.setIsDeleted("N");
			} else {
				group.setIsDeleted(request.getIsDeleted());
			}
			groupServiceImp.updateGroup(group);
			// response
			Group gr = groupServiceImp.findByName(request.getName());
			response.setId(gr.getId());
			response.setName(gr.getName());
			response.setDescription(gr.getDescription());
			response.setIsDeleted(gr.getIsDeleted());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	// delete
	@DeleteMapping(value = "/deleted/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		GroupResponse response = new GroupResponse();
		Optional<Group> group = groupServiceImp.findById(id);
		if (!group.isPresent()) {
			response.setMessage("Group " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else {
			groupServiceImp.deletedGroup(id);
			response.setMessage("Group " + id + " deleted");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	

	// user group
	@PostMapping(value = "/ug/mapping")
	public ResponseEntity<?> createUserGroup(@RequestBody UserGroupRequest request) {
		UserGroupResponse response = new UserGroupResponse();
		List<UserGroup> userGroups = request.getMapping();
		User user;
		Group group;
		// check exist 
		for (UserGroup userGroup : userGroups) {
			user = userRepository.findUserById(userGroup.getUserId());
			group = groupRepository.findGroupById(userGroup.getGroupId());
			if (user == null || group == null) {
				response.setMessage("User or Group does not exist");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
		for (UserGroup listMapping : userGroups) {
			user = userRepository.findUserById(listMapping.getUserId());
			user.getGroups().add(groupRepository.findGroupById(listMapping.getGroupId()));
			userRepository.save(user);
		}
		response.setMessage("Add User Group is success");
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
	// group permission
	@PostMapping(value = "/gp/mapping")
	public ResponseEntity<?> createGroupPermission(@RequestBody GroupPerRequest request) {
		GroupPerResponse response = new GroupPerResponse();
		List<GroupPermission> groupPermissions = request.getMapping();
		Group group;
		Permission permission;
		for(GroupPermission groupPermission : groupPermissions) {
			group = groupRepository.findGroupById(groupPermission.getGroupId());
			permission = permissionRepo.findPermissionById(groupPermission.getPermissionId());
			if(group == null || permission == null ) {
				response.setMessage("Group or Permission does not exist");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
		for (GroupPermission listMapping : groupPermissions) {
			group = groupRepository.findGroupById(listMapping.getGroupId());
			group.getPermissions().add(permissionRepo.findPermissionById(listMapping.getPermissionId()));
			groupRepository.save(group);
		}
		response.setMessage("Add Group Permission is success ");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
