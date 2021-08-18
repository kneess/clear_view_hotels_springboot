package com.persholas.services;

import com.persholas.dao.IRoomExpenseRepo;
import com.persholas.dao.IRoomRepo;
import com.persholas.models.Room;
import com.persholas.models.RoomExpense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class RoomService {

    private IRoomRepo roomRepo;
    private IRoomExpenseRepo roomExpenseRepo;

    @Autowired
    public RoomService(IRoomRepo roomRepo, IRoomExpenseRepo roomExpenseRepo)
    {
        this.roomRepo = roomRepo;
        this.roomExpenseRepo = roomExpenseRepo;
    }

    public List<Room> getAllRooms()
    {
        log.warn("RoomService: Executing: getAllRooms");
        return roomRepo.findAll();
    }

    public Room getRoomById(Long id)
    {
        log.warn("RoomService: Executing: getRoomById");
        return roomRepo.getById(id);
    }

    public List<RoomExpense> getAllRoomExpensesByRoom(Room room)
    {
        log.warn("RoomService: Executing: getAllRoomExpensesByRoom");
        return roomExpenseRepo.getRoomExpensesByRoom(room);
    }
}
