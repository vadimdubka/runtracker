package com.vdibka.runtracker.service;

import com.vdibka.runtracker.entity.User;

public interface UserService {
    void save(User user);
    
    User findByUsername(String username);
}
