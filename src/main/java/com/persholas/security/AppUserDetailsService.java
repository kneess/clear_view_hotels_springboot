package com.persholas.security;

import com.persholas.dao.IAuthGroupRepo;
import com.persholas.dao.ICustomerRepo;
import com.persholas.dao.IEmployeeRepo;
import com.persholas.models.AuthGroup;
import com.persholas.models.Customer;
import com.persholas.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final IEmployeeRepo employeeRepo;
    private final ICustomerRepo customerRepo;
    private final IAuthGroupRepo authGroupRepo;

    @Autowired
    public AppUserDetailsService(IEmployeeRepo employeeRepo,ICustomerRepo customerRepo,IAuthGroupRepo authGroupRepo)
    {
        this.employeeRepo = employeeRepo;
        this.customerRepo = customerRepo;
        this.authGroupRepo = authGroupRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepo.findBycUsername(s);
        Optional<Employee> employee = employeeRepo.findByeUsername(s);
        if(customer.isEmpty() && employee.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find Username: " + s);
        }

        List<AuthGroup> authGroups = this.authGroupRepo.findByaUsername(s);
        AppUserPrincipal appUserPrincipal = null;
        if(customer.isPresent()){
            appUserPrincipal = new AppUserPrincipal(customer.get(),authGroups);
        }
        if(employee.isPresent()){
            appUserPrincipal = new AppUserPrincipal(employee.get(), authGroups);
        }
        return appUserPrincipal;
    }
}