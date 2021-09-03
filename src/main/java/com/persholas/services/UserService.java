package com.persholas.services;

import com.persholas.dao.IUserRepo;
import com.persholas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    IUserRepo userRepo;

    @Autowired
    public UserService(IUserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public User getUserById(Long id)
    {
        return userRepo.getById(id);
    }
}
