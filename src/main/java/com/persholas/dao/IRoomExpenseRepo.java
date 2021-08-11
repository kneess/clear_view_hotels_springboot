package com.persholas.dao;

import com.persholas.models.RoomExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomExpenseRepo extends JpaRepository<RoomExpense, Long> {
}
