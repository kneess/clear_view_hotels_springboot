package com.persholas.controllers;

import com.persholas.models.Customer;
import com.persholas.models.Employee;
import com.persholas.models.Hotel;
import com.persholas.services.CustomerService;
import com.persholas.services.EmployeeService;
import com.persholas.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/clearview")
public class CustomersController {

    CustomerService customerService;
    HotelService hotelService;
    @Autowired
    public CustomersController(CustomerService customerService, HotelService hotelService)
    {
        this.customerService = customerService;
        this.hotelService = hotelService;
    }

    @GetMapping("/customers")
    public String customers(Model model)
    {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers",customers);
        return "customers";
    }

    @GetMapping("/hotels/{hotelId}/customers/form")
    public String customerForm(@PathVariable("hotelId") Long hotelId, Model model)
    {
        Hotel hotel = hotelService.getHotelById(hotelId);
        Customer customer = new Customer();
        customer.setActive(false);
        model.addAttribute("hotel",hotel);
        model.addAttribute("customer",customer);
        return "newCustomerForm";
    }

    @PostMapping("/hotels/{hotelId}/customers")
    public String addCustomer(@PathVariable("hotelId") Long hotelId
            , @ModelAttribute("customer") @Valid Customer customer,
                            BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            log.warn(bindingResult.getAllErrors().toString());
            return "newCustomerForm";
        }
        Customer newCustomer = customerService.addNewCustomer(customer);
        hotelService.addCustomerToHotel(hotelId, newCustomer);
        return "redirect:/clearview/hotels/"+ hotelId +"/customers";
    }

}
