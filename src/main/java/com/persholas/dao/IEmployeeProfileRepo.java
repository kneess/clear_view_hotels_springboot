package com.persholas.dao;

import com.persholas.models.EmployeeProfile;
import com.persholas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmployeeProfileRepo extends JpaRepository<EmployeeProfile, Long> {
    EmployeeProfile getByUser(User user);
}
