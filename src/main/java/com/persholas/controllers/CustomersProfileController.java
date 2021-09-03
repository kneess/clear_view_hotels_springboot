package com.persholas.controllers;

import com.persholas.dao.IAuthGroupRepo;
import com.persholas.dao.IUserRepo;
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
public class CustomersProfileController {

    CustomerProfileService customerService;
    HotelService hotelService;
    RoomService roomService;
    IAuthGroupRepo authGroupRepo;
    IUserRepo userRepo;
    @Autowired
    public CustomersProfileController(CustomerProfileService customerService, HotelService hotelService,
                                      RoomService roomService, IAuthGroupRepo authGroupRepo, IUserRepo userRepo)
    {
        this.customerService = customerService;
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.authGroupRepo = authGroupRepo;
        this.userRepo = userRepo;
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
        User newUser = new User();
        customer.setActive(false);
        model.addAttribute("hotel",hotel);
        model.addAttribute("user",newUser);
        model.addAttribute("customer",customer);
        return "newCustomerForm";
    }

    @PostMapping("/hotels/{hotelId}/customers")
    public String addCustomer(@PathVariable("hotelId") Long hotelId
            , @ModelAttribute("customer") @Valid CustomerProfile customerProfile,
                            BindingResult bindingResultCustomerProfile,
                              @ModelAttribute("user") @Valid User user,
                              BindingResult bindingResultUser,Model model)
    {
        //pass as attribute
        Hotel hotel = hotelService.getHotelById(hotelId);

        if(bindingResultCustomerProfile.hasErrors() || bindingResultUser.hasErrors())
        {
            model.addAttribute("hotel",hotel);
            return "newCustomerForm";
        }
        // check for username before creating customerProfile
        //conflict because of Employee and Customer entity use with security auth
        if(authGroupRepo.findByaUsername(user.getEmail()).isEmpty()) {

            authGroupRepo.save(new AuthGroup(user.getEmail(), "ROLE_CUSTOMER"));
            //encrypting customerProfile password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getUPassword());
            user.setUPassword(encodedPassword);
            User newUser = userRepo.save(user);
            customerProfile.setUser(newUser);
            CustomerProfile newCustomer = customerService.addOrUpdateCustomer(customerProfile);
            hotelService.addCustomerToHotel(hotelId, newCustomer);
            return "redirect:/clearview/hotels/" + hotelId + "/customers";
        }
        //send customerProfile error to customerProfile form if username
        String usernameExists = "Email is already in our database";
        model.addAttribute("usernameExists",usernameExists);
        model.addAttribute("hotel",hotel);
        model.addAttribute("customer",customerProfile);
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
