package com.vdibka.runtracker.dto;

import java.time.LocalDate;

public class ReportDTO {
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    
    private Double avrSpeed;
    private Double avrTime;
    private Double totalDistance;
    
    public LocalDate getWeekStartDate() {
        return weekStartDate;
    }
    
    public void setWeekStartDate(LocalDate weekStartDate) {
        this.weekStartDate = weekStartDate;
    }
    
    public LocalDate getWeekEndDate() {
        return weekEndDate;
    }
    
    public void setWeekEndDate(LocalDate weekEndDate) {
        this.weekEndDate = weekEndDate;
    }
    
    public Double getAvrSpeed() {
        return avrSpeed;
    }
    
    public void setAvrSpeed(Double avrSpeed) {
        this.avrSpeed = avrSpeed;
    }
    
    public Double getAvrTime() {
        return avrTime;
    }
    
    public void setAvrTime(Double avrTime) {
        this.avrTime = avrTime;
    }
    
    public Double getTotalDistance() {
        return totalDistance;
    }
    
    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }
}
