package com.persholas.services;

import com.persholas.dao.IEmployeeRepo;
import com.persholas.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class EmployeeService {

    private IEmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(IEmployeeRepo employeeRepo)
    {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> getAllEmployees()
    {
        log.warn("EmployeeService: Executing: getAllEmployees");
        return employeeRepo.findAll();
    }

    public Employee getEmployeeById(Long id)
    {
        log.warn("EmployeeService: Executing: getEmployeeById");
        return employeeRepo.getById(id);
    }

    public void addNewEmployee(Employee employee)
    {
        employeeRepo.save(employee);
    }
}
