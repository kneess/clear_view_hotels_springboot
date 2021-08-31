package com.persholas.controllers;

import com.persholas.models.CustomerProfile;
import com.persholas.services.CustomerProfileService;
import com.persholas.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("clearview")
public class CustomerRoomController {

    CustomerProfileService customerService;
    UserService userService;

    @Autowired
    public CustomerRoomController(CustomerProfileService customerService, UserService userService)
    {
        this.customerService = customerService;
        this.userService = userService;
    }

    //if customer(ROLE_CUSTOMER) logged in get username
    @GetMapping("/myroom/{email}")
    public String getMyRoom(@PathVariable("email") String email, Model model)
    {
        CustomerProfile optionalCustomer = customerService.getCustomerProfileByUser(userService.getUserByEmail(email));
//        CustomerProfile customer = optionalCustomer.get();
        model.addAttribute("customer",optionalCustomer);

        return "customerRoom";
    }
}
