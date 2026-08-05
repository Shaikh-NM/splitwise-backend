package com.splitwise.usergroup.repository;

import com.splitwise.usergroup.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {}