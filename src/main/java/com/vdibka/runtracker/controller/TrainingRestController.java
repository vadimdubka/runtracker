package com.vdibka.runtracker.controller;

import com.vdibka.runtracker.dao.TrainingRepository;
import com.vdibka.runtracker.dao.UserRepository;
import com.vdibka.runtracker.dto.ReportDTO;
import com.vdibka.runtracker.entity.Training;
import com.vdibka.runtracker.exception.ResourceNotFoundException;
import com.vdibka.runtracker.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/trainings")
public class TrainingRestController {
    
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainingService trainingService;
    
    @Autowired
    public TrainingRestController(TrainingRepository trainingRepository, UserRepository userRepository, TrainingService trainingService) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.trainingService = trainingService;
    }
    
    @GetMapping()
    public List<Training> retrieveAllTrainings() {
        return trainingRepository.findAll();
    }
    
    @GetMapping("/user/{userId}")
    public List<Training> retrieveAllTrainings(@PathVariable("userId") Long userId) {
        return trainingRepository.findByUserIdOrderByDateDesc(userId);
    }
    
    @GetMapping("/{id}")
    public Training retrieveTraining(@PathVariable long id) {
        Optional<Training> training = trainingRepository.findById(id);
        return training.orElse(null);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable long id) {
        trainingRepository.deleteById(id);
    }
    
    @PostMapping("/{userId}")
    public Training createTraining(@RequestBody Training training,
                                   @PathVariable("userId") Long userId) {
        return userRepository.findById(userId)
                   .map(user -> {
                       training.setUser(user);
                       return trainingRepository.save(training);
                   })
                   .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found."));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTraining(@RequestBody Training training, @PathVariable Long id) {
        Optional<Training> trainingOptional = trainingRepository.findById(id);
        
        if (!trainingOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        training.setId(id);
        trainingRepository.save(training);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/report/{userId}")
    public List<ReportDTO> getReportForUser(@PathVariable("userId") Long userId) {
        return trainingService.createReport(userId);
    }
    
}
