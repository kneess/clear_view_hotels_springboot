package com.persholas.controllers;

import com.persholas.models.Customer;
import com.persholas.models.Employee;
import com.persholas.services.CustomerService;
import com.persholas.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/clearview")
public class CustomersController {

    CustomerService customerService;
    @Autowired
    public CustomersController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String customers(Model model)
    {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers",customers);
        return "customers";
    }
}
