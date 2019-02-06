package com.vdibka.runtracker.dao;

import com.vdibka.runtracker.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUserIdOrderByDateDesc(Long userId);
    
    List<Training> findByUserId(Long userId);
}
