package com.persholas.controllers;

import com.persholas.dao.IAuthGroupRepo;
import com.persholas.models.*;
import com.persholas.services.CustomerProfileService;
import com.persholas.services.HotelService;
import com.persholas.services.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("clearview")
public class CustomersController {

    CustomerProfileService customerService;
    HotelService hotelService;
    RoomService roomService;
    IAuthGroupRepo authGroupRepo;
    @Autowired
    public CustomersController(CustomerProfileService customerService, HotelService hotelService, RoomService roomService, IAuthGroupRepo userRepo)
    {
        this.customerService = customerService;
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.authGroupRepo = userRepo;
    }

    @GetMapping("/customers")
    public String customers(Model model)
    {
        List<CustomerProfile> customers = customerService.getAllCustomers();
        model.addAttribute("customers",customers);
        return "customers";
    }

    @GetMapping("/hotels/{hotelId}/customers/form")
    public String customerForm(@PathVariable("hotelId") Long hotelId, Model model)
    {
        Hotel hotel = hotelService.getHotelById(hotelId);
        CustomerProfile customer = new CustomerProfile();
        // set active to false so thymeleaf form radio box checked
        customer.setActive(false);
        model.addAttribute("hotel",hotel);
        model.addAttribute("customer",customer);
        return "newCustomerForm";
    }

    @PostMapping("/hotels/{hotelId}/customers")
    public String addCustomer(@PathVariable("hotelId") Long hotelId
            , @ModelAttribute("customer") @Valid CustomerProfile customer,
                            BindingResult bindingResult,Model model)
    {
        if(bindingResult.hasErrors())
        {
            log.warn(bindingResult.getAllErrors().toString());
            return "newCustomerForm";
        }
        // check for username before creating customer
        //conflict because of Employee and Customer entity use with security auth
        if(authGroupRepo.findByaUsername(customer.getUser().getEmail()).isEmpty()) {
            //encrypting customer password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(customer.getUser().getUPassword());
            customer.getUser().setUPassword(encodedPassword);
            CustomerProfile newCustomer = customerService.addOrUpdateCustomer(customer);
            hotelService.addCustomerToHotel(hotelId, newCustomer);
            authGroupRepo.save(new AuthGroup(newCustomer.getUser().getEmail(), "ROLE_CUSTOMER"));
            return "redirect:/clearview/hotels/" + hotelId + "/customers";
        }
        //send customer error to customer form if username
        String usernameExists = "Username already taken please enter in another";
        Hotel hotel = hotelService.getHotelById(hotelId);
        model.addAttribute("usernameExists",usernameExists);
        model.addAttribute("hotel",hotel);
        model.addAttribute("customer",customer);
        return "newCustomerForm";
    }

    @GetMapping("/hotels/{hotelId}/customers/{customerId}/assignroom")
    public String assignCustomerToRoomForm(@PathVariable("hotelId") Long hotelId, @PathVariable("customerId") Long customerId,
            Model model)
    {
        //get vacant rooms only before displaying available rooms to customer being assigned room
        Hotel hotel = hotelService.getHotelById(hotelId);
        CustomerProfile customer = customerService.getCustomerById(customerId);
        List<Room> rooms = hotel.getRooms();
        List<Room> vacantRooms = new ArrayList<>();
        for(Room r : rooms)
        {
            if(r.getVacancy()){
                vacantRooms.add(r);
            }
        }
        model.addAttribute("hotel",hotel);
        model.addAttribute("customer",customer);
        model.addAttribute("rooms", vacantRooms);
        return "assignRoom";
    }

    @PostMapping("/hotels/{hotelId}/customers/{customerId}/room")
    public String assiningRoom(@PathVariable("customerId") Long customerId,@PathVariable("hotelId") Long hotelId
            , @RequestParam("roomId") Long roomId)
    {
        CustomerProfile customer = customerService.getCustomerById(customerId);
        Room room = roomService.assignCustomerToRoom(roomId,customer);
        customer.setRoom(room);
        customerService.addOrUpdateCustomer(customer);
        return "redirect:/clearview/hotels/"+ hotelId +"/customers";
    }
}
