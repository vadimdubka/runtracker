package com.vdibka.runtracker.service;

import com.vdibka.runtracker.dao.TrainingRepository;
import com.vdibka.runtracker.dto.ReportDTO;
import com.vdibka.runtracker.entity.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    
    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }
    
    @Override
    public List<ReportDTO> createReport(Long userId) {
        List<Training> trainings = trainingRepository.findByUserId(userId);
        Map<LocalDate, Set<Training>> trainingsByWeeks = mapTrainingsByWeeks(trainings);
        List<ReportDTO> reportList = trainingsByWeeks.entrySet().stream()
                                         .map(this::getReportDTO)
                                         .collect(Collectors.toList());
        return reportList;
    }
    
    private Map<LocalDate, Set<Training>> mapTrainingsByWeeks(List<Training> trainings) {
        Map<LocalDate, Set<Training>> trainingsByWeeks = new TreeMap<>();
        for (Training training : trainings) {
            LocalDate trainingDate = convertToLocalDate(training.getDate());
            LocalDate mondayOfTrainingWeek = trainingDate.with(DayOfWeek.MONDAY);
            Set<Training> trainingsForWeek = trainingsByWeeks.get(mondayOfTrainingWeek);
            if (trainingsForWeek == null) {
                Set<Training> map = new HashSet<>();
                map.add(training);
                trainingsByWeeks.put(mondayOfTrainingWeek, map);
            } else {
                trainingsForWeek.add(training);
            }
        }
        return trainingsByWeeks;
    }
    
    private ReportDTO getReportDTO(Map.Entry<LocalDate, Set<Training>> enty) {
        ReportDTO report = new ReportDTO();
        LocalDate mondayOfTrainingWeek = enty.getKey();
        LocalDate sundayOfTrainingWeek = mondayOfTrainingWeek.plusDays(6);
        report.setWeekStartDate(mondayOfTrainingWeek);
        report.setWeekEndDate(sundayOfTrainingWeek);
        
        Set<Training> trainingsInWeek = enty.getValue();
        int trainingsNumber = trainingsInWeek.size();
        double totalDistance = 0;
        double totalTime = 0;
        for (Training training : trainingsInWeek) {
            totalDistance += training.getDistance();
            totalTime+=training.getTime();
        }
        double avrSpeed = totalDistance/ totalTime;
        double avrTime = totalTime / trainingsNumber;
        report.setAvrSpeed(avrSpeed);
        report.setAvrTime(avrTime);
        report.setTotalDistance(totalDistance);
        return report;
    }
    
    private LocalDate convertToLocalDate(Date dateToConvert) {
        Instant instant = new Date(dateToConvert.getTime()).toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate date = zdt.toLocalDate();
        return date;
    }
}
