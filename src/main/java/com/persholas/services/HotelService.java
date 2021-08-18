package com.persholas.services;

import com.persholas.dao.IHotelExpenseRepo;
import com.persholas.dao.IHotelRepo;
import com.persholas.models.Hotel;
import com.persholas.models.HotelExpense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelService {
    private IHotelRepo hotelRepo;
    private IHotelExpenseRepo hotelExpenseRepo;
    @Autowired
    public HotelService(IHotelRepo hotelRepo, IHotelExpenseRepo hotelExpenseRepo)
    {
        this.hotelRepo = hotelRepo;
        this.hotelExpenseRepo = hotelExpenseRepo;
    }

    public List<Hotel> getAllHotels()
    {
        log.warn("HotelService: getAllHotels");
        return hotelRepo.findAll();
    }

    public Hotel getHotelById(Long id)
    {
        log.warn("HotelService: getHotelById");
        return hotelRepo.getById(id);
    }

    public List<HotelExpense> getAllHotelExpensesByHotel(Hotel hotel)
    {
        log.warn("HotelService: getAllHotelExpensesByHotel");
        return hotelExpenseRepo.getAllByHotel(hotel);
    }

}
