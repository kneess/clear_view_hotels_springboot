package com.persholas.controllers;

import com.persholas.dao.IAuthGroupRepo;
import com.persholas.dao.IUserRepo;
import com.persholas.models.CustomerProfile;
import com.persholas.models.Hotel;
import com.persholas.models.Room;
import com.persholas.models.User;
import com.persholas.services.CustomerProfileService;
import com.persholas.services.HotelService;
import com.persholas.services.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("clearview")
public class HotelCatalogController {

    HotelService hotelService;
    CustomerProfileService customerService;
    RoomService roomService;
    IUserRepo userRepo;

    @Autowired
    public HotelCatalogController(HotelService hotelService,CustomerProfileService customerService,RoomService roomService,
                                  IUserRepo userRepo)
    {
        this.hotelService = hotelService;
        this.customerService = customerService;
        this.roomService = roomService;
        this.userRepo = userRepo;
    }

    @GetMapping("/myroom/{userid}/catalog")
    public String getCatalog(Model model)
    {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels",hotels);
        return "hotelCatalog";
    }

    @GetMapping("/myroom/{userid}/catalog/{hotelid}")
    public String getRoomCatalog(@PathVariable("hotelid")Long hotelId, Model model)
    {
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<Room> rooms = hotel.getRooms();
        model.addAttribute("hotel",hotel);
        model.addAttribute("rooms",rooms);
        return "roomCatalog";
    }

    @GetMapping("/myroom/{userid}/catalog/{hotelid}/room/{roomid}")
    public String getRoomDetails(@PathVariable("roomid")Long roomId, Model model)
    {
        Room room = roomService.getRoomById(roomId);
        model.addAttribute("room",room);
        return "roomDetail";
    }

    @PostMapping("/myroom/{userid}/catalog/{hotelid}/room/{roomid}/book")
    public String bookRoom(@PathVariable("userid")Long userId,@PathVariable("hotelid")Long hotelId, @PathVariable("roomid")Long roomId,
                           Model model)
    {
        User user = userRepo.getById(userId);
        CustomerProfile customerProfile = customerService.getCustomerProfileByUser(user);
        Hotel hotel = hotelService.getHotelById(hotelId);
        Room room = roomService.getRoomById(roomId);
        customerProfile.setHotel(hotel);
        customerProfile.setRoom(room);
        CustomerProfile updatedCustomer = customerService.addOrUpdateCustomer(customerProfile);
        hotelService.addCustomerToHotel(hotelId,updatedCustomer);
        roomService.assignCustomerToRoom(roomId,updatedCustomer);
        return "bookSuccess";
    }
}
