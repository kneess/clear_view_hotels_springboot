package com.persholas.dao;

import com.persholas.models.Hotel;
import com.persholas.models.HotelExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHotelExpenseRepo extends JpaRepository<HotelExpense, Long> {
    List<HotelExpense> getAllByHotel(Hotel hotel);
}
