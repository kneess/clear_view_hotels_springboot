package com.persholas.dao;

import com.persholas.models.Room;
import com.persholas.models.RoomExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomExpenseRepo extends JpaRepository<RoomExpense, Long> {
    List<RoomExpense> getRoomExpensesByRoom(Room room);
}
