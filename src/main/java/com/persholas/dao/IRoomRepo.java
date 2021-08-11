package com.persholas.dao;

import com.persholas.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepo extends JpaRepository<Room, Long> {
}
