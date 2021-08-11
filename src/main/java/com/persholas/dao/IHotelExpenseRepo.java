package com.persholas.dao;

import com.persholas.models.HotelExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelExpenseRepo extends JpaRepository<HotelExpense, Long> {
}
