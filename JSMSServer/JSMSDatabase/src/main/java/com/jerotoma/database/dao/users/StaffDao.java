package com.jerotoma.database.dao.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jerotoma.common.models.users.Staff;

public interface StaffDao extends JpaRepository<Staff, Integer> {

}
