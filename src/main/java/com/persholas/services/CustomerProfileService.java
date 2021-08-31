package com.persholas.services;

import com.persholas.dao.ICustomerProfileRepo;
import com.persholas.models.CustomerProfile;
import com.persholas.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CustomerProfileService {

    private ICustomerProfileRepo customerRepo;

    @Autowired
    public CustomerProfileService(ICustomerProfileRepo customerRepo)
    {
        this.customerRepo = customerRepo;
    }

    public List<CustomerProfile> getAllCustomers()
    {
        log.warn("CustomerService: Executing: getAllCustomers");
        return customerRepo.findAll();
    }

    public CustomerProfile getCustomerById(Long id)
    {
        log.warn("CustomerService: Executing: getCustomerById");
        return customerRepo.getById(id);
    }


    public CustomerProfile addOrUpdateCustomer(CustomerProfile customer)
    {
        return customerRepo.save(customer);
    }

    public CustomerProfile getCustomerProfileByUser(Optional<User> user) {
        return customerRepo.findByUser(user);
    }
}
