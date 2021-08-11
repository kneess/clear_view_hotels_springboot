package com.persholas.dao;

import com.persholas.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepo extends JpaRepository<Hotel, Long> {
}
