package com.persholas.controllers;

import com.persholas.models.Customer;
import com.persholas.models.Room;
import com.persholas.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("clearview")
public class CustomerRoomController {

    CustomerService customerService;

    @Autowired
    public CustomerRoomController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @GetMapping("/myroom/{username}")
    public String getMyRoom(@PathVariable("username") String username, Model model)
    {
        Optional<Customer> optionalCustomer = customerService.getCustomerByUsername(username);
        Customer customer = optionalCustomer.get();
        model.addAttribute("customer",customer);

        return "customerRoom";
    }
}
