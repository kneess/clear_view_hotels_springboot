package com.persholas.controllers;

import com.persholas.models.Hotel;
import com.persholas.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("clearview")
public class CatalogController {

    HotelService hotelService;

    @Autowired
    public CatalogController(HotelService hotelService)
    {
        this.hotelService = hotelService;
    }

    @GetMapping("/myroom/{userid}/catalog")
    public String getCatalog(Model model)
    {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels",hotels);
        return "catalog";
    }
}
