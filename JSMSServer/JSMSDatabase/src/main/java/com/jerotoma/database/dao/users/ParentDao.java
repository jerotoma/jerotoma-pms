package com.jerotoma.database.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jerotoma.common.models.users.Parent;

public interface ParentDao extends JpaRepository<Parent, Integer> {

}
