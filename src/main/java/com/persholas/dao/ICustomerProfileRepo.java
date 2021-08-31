package com.persholas.dao;

import com.persholas.models.CustomerProfile;
import com.persholas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerProfileRepo extends JpaRepository<CustomerProfile, Long> {
    CustomerProfile findByUser(Optional<User> user);
}
