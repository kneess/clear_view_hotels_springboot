package com.persholas.dao;

import com.persholas.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByeUsername(String username);
}
