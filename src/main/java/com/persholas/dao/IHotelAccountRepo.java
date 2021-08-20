package com.persholas.dao;

import com.persholas.models.Hotel;
import com.persholas.models.HotelAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHotelAccountRepo extends JpaRepository<HotelAccount, Long> {
}
