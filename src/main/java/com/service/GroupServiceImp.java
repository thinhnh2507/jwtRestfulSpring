package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Group;
import com.repository.GroupRepository;

@Service
public class GroupServiceImp implements GroupService {
	@Autowired
	GroupRepository groupRepository;

	@Override
	public Optional<Group> findById(long id) {
		return groupRepository.findById(id);
	}

	@Override
	public Group findByName(String name) {
		return groupRepository.findGroupByName(name);
	}

	@Override
	public void createGroup(Group group) {
		groupRepository.save(group);
	}

	@Override
	public void updateGroup(Group group) {
		groupRepository.save(group);
	}

	@Override
	public void deletedGroup(long id) {
		Group group = groupRepository.findGroupById(id);
		group.setIsDeleted("Y");
		groupRepository.save(group);
	}

}
