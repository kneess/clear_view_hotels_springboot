package com.persholas.services;

import com.persholas.dao.ICustomerRepo;
import com.persholas.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CustomerService {

    private ICustomerRepo customerRepo;

    @Autowired
    public CustomerService(ICustomerRepo customerRepo)
    {
        this.customerRepo = customerRepo;
    }

    public List<Customer> getAllCustomers()
    {
        log.warn("CustomerService: Executing: getAllCustomers");
        return customerRepo.findAll();
    }

    public Customer getCustomerById(Long id)
    {
        log.warn("CustomerService: Executing: getCustomerById");
        return customerRepo.getById(id);
    }

    public Customer addNewCustomer(Customer customer)
    {
        return customerRepo.save(customer);
    }

}
