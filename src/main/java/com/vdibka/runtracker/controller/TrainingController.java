package com.vdibka.runtracker.controller;

import com.vdibka.runtracker.dao.TrainingRepository;
import com.vdibka.runtracker.entity.Training;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class TrainingController {
    
    private final TrainingRepository trainingRepository;
    
    @Autowired
    public TrainingController(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }
    
    @GetMapping("/trainings")
    public List<Training> retrieveAllTrainings() {
        return trainingRepository.findAll();
    }
    
    @GetMapping("/trainings/{id}")
    public Training retrieveTraining(@PathVariable long id) {
        Optional<Training> training = trainingRepository.findById(id);
        return training.orElse(null);
    }
    
    @DeleteMapping("/trainings/{id}")
    public void deleteTraining(@PathVariable long id) {
        trainingRepository.deleteById(id);
    }
    
    @PostMapping("/trainings")
    public ResponseEntity<Object> createTraining(@RequestBody Training training) {
        Training savedTraining = trainingRepository.save(training);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                           .buildAndExpand(savedTraining.getId()).toUri();
        
        return ResponseEntity.created(location).build();
    }
    
    @PutMapping("/trainings/{id}")
    public ResponseEntity<Object> updateTraining(@RequestBody Training training, @PathVariable long id) {
        Optional<Training> trainingOptional = trainingRepository.findById(id);
        
        if (!trainingOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        
        training.setId(id);
        trainingRepository.save(training);
        return ResponseEntity.noContent().build();
    }
}
