package com.persholas.controllers;

import com.persholas.models.CustomerProfile;
import com.persholas.models.EmployeeProfile;
import com.persholas.models.Hotel;
import com.persholas.models.Room;
import com.persholas.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("clearview")
public class HotelsController {

    HotelService hotelService;

    @Autowired
    public HotelsController(HotelService hotelService)
    {
        this.hotelService = hotelService;
    }

    //ROLE_ADMIN, ROLE_CUSTOMER has access
    //show all hotels
    @GetMapping("/hotels")
    public String hotels(Model model)
    {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels",hotels);
        return "hotels";
    }

    //ROLE_ADMIN, ROLE_CUSTOMER has access
    //show hotel
    @GetMapping("/hotels/{id}")
    public String showHotel(@PathVariable("id") Long hotelId, Model model)
    {
        Hotel foundHotel = hotelService.getHotelById(hotelId);
        List<Room> hotelRooms = foundHotel.getRooms();
        model.addAttribute("hotel", foundHotel);
        model.addAttribute("rooms",hotelRooms);
        return "hotel";
    }

    //ROLE_ADMIN has access
    //show hotel employees
    @GetMapping("/hotels/{id}/employees")
    public String showHotelEmployees(@PathVariable("id") Long hotelId, Model model)
    {
        Hotel foundHotel = hotelService.getHotelById(hotelId);
        List<EmployeeProfile> employees = foundHotel.getEmployees();
        model.addAttribute("hotel",foundHotel);
        model.addAttribute("employees",employees);
        return "employees";
    }

    //ROLE_ADMIN, ROLE_CUSTOMER has access
    //show hotel customers
    @GetMapping("/hotels/{id}/customers")
    public String showHotelCustomers(@PathVariable("id") Long hotelId, Model model)
    {
        Hotel foundHotel = hotelService.getHotelById(hotelId);
        List<CustomerProfile> customers = foundHotel.getCustomers();
        model.addAttribute("hotel",foundHotel);
        model.addAttribute("customers",customers);
        return "customers";
    }
}
