package com.persholas.dao;


import com.persholas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User, Long> {
    //needed for spring security
    Optional<User> findByEmail(String email);
}
