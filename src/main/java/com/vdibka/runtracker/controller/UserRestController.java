package com.vdibka.runtracker.controller;

import com.vdibka.runtracker.dao.TrainingRepository;
import com.vdibka.runtracker.dao.UserRepository;
import com.vdibka.runtracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserRestController {
    
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    
    @Autowired
    public UserRestController(UserRepository userRepository, TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }
    
    @GetMapping()
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
}
