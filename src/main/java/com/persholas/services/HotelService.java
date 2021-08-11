package com.persholas.services;

import com.persholas.dao.IHotelRepo;
import com.persholas.models.Hotel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelService {
    private IHotelRepo hotelRepo;
    @Autowired
    public HotelService(IHotelRepo hotelRepo)
    {
        this.hotelRepo = hotelRepo;
    }

    public List<Hotel> getAllHotels()
    {
        log.warn("HotelService: getAllHotels()");
        return hotelRepo.findAll();
    }
}
