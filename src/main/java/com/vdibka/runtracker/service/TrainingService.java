package com.vdibka.runtracker.service;

import com.vdibka.runtracker.dto.ReportDTO;

import java.util.List;

public interface TrainingService {
    List<ReportDTO> createReport(Long userId);
}
