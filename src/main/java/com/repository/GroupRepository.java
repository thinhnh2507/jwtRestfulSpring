package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Group;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
Group findGroupByName(String name);
Group findGroupById(long id);
}
