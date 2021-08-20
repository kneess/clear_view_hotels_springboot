package com.persholas.services;

import com.persholas.dao.IHotelAccountRepo;
import com.persholas.dao.IHotelRepo;
import com.persholas.models.Employee;
import com.persholas.models.Hotel;
import com.persholas.models.HotelAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class HotelService {
    private IHotelRepo hotelRepo;
    private IHotelAccountRepo hotelAccountRepo;
    @Autowired
    public HotelService(IHotelRepo hotelRepo, IHotelAccountRepo hotelAccountRepo)
    {
        this.hotelRepo = hotelRepo;
        this.hotelAccountRepo = hotelAccountRepo;
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
}
