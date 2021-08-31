package com.persholas.services;

import com.persholas.dao.IHotelRepo;
import com.persholas.models.CustomerProfile;
import com.persholas.models.EmployeeProfile;
import com.persholas.models.Hotel;
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
    @Autowired
    public HotelService(IHotelRepo hotelRepo)
    {
        this.hotelRepo = hotelRepo;
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

    public void addEmployeeToHotel(Long hotelId, EmployeeProfile employee)
    {
        Hotel hotel = getHotelById(hotelId);
        List<EmployeeProfile> employees = hotel.getEmployees();
        employees.add(employee);
        hotel.setEmployees(employees);
        hotelRepo.save(hotel);
    }
    public void addCustomerToHotel(Long hotelId, CustomerProfile customer)
    {
        Hotel hotel = getHotelById(hotelId);
        List<CustomerProfile> customers = hotel.getCustomers();
        customers.add(customer);
        hotel.setCustomers(customers);
        customer.setHotel(hotel);
        hotelRepo.save(hotel);
    }
}
