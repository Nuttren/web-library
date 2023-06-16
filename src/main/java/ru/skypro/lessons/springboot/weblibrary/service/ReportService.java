package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;

public interface ReportService {
    int generateReport();

    ReportDTO getReportById(long id);

    String getReportContentById(Long id);

}
