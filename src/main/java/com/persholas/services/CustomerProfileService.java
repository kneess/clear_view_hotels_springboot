package com.persholas.services;

import com.persholas.dao.ICustomerProfileRepo;
import com.persholas.models.CustomerProfile;
import com.persholas.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CustomerProfileService {

    private ICustomerProfileRepo customerProfileRepo;

    @Autowired
    public CustomerProfileService(ICustomerProfileRepo customerRepo)
    {
        this.customerProfileRepo = customerRepo;
    }

    public List<CustomerProfile> getAllCustomers()
    {
        log.warn("CustomerService: Executing: getAllCustomers");
        return customerProfileRepo.findAll();
    }

    public CustomerProfile getCustomerById(Long id)
    {
        log.warn("CustomerService: Executing: getCustomerById");
        return customerProfileRepo.getById(id);
    }


    public CustomerProfile addOrUpdateCustomer(CustomerProfile customer)
    {
        return customerProfileRepo.save(customer);
    }

    public CustomerProfile getCustomerProfileByUser(User user) {
        return customerProfileRepo.findByUser(user);
    }
}
