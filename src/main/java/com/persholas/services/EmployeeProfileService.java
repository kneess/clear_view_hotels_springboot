package com.persholas.services;

import com.persholas.dao.IEmployeeProfileRepo;
import com.persholas.models.EmployeeProfile;
import com.persholas.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class EmployeeProfileService {

    private IEmployeeProfileRepo employeeRepo;

    @Autowired
    public EmployeeProfileService(IEmployeeProfileRepo employeeRepo)
    {
        this.employeeRepo = employeeRepo;
    }

    public List<EmployeeProfile> getAllEmployeeProfiles()
    {
        return employeeRepo.findAll();
    }

    public EmployeeProfile getEmployeeById(Long id)
    {
        return employeeRepo.getById(id);
    }

    public EmployeeProfile getEmployeeByUser(User user)
    {
        return employeeRepo.getByUser(user);
    }

    public EmployeeProfile addNewEmployeeProfile(EmployeeProfile employee)
    {
        return employeeRepo.save(employee);
    }
}
