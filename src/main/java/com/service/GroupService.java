package com.service;

import java.util.Optional;

import com.model.Group;

public interface GroupService {
	Optional<Group> findById(long id);

	Group findByName(String name);

	void createGroup(Group group);

	void updateGroup(Group group);

	void deletedGroup(long id);
}
