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

    private IEmployeeProfileRepo employeeProfileRepo;

    @Autowired
    public EmployeeProfileService(IEmployeeProfileRepo employeeRepo)
    {
        this.employeeProfileRepo = employeeRepo;
    }

    public List<EmployeeProfile> getAllEmployeeProfiles()
    {
        return employeeProfileRepo.findAll();
    }

    public EmployeeProfile getEmployeeById(Long id)
    {
        return employeeProfileRepo.getById(id);
    }

    public EmployeeProfile getEmployeeByUser(User user)
    {
        return employeeProfileRepo.getByUser(user);
    }

    public EmployeeProfile addNewEmployeeProfile(EmployeeProfile employee)
    {
        return employeeProfileRepo.save(employee);
    }
}
