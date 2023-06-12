package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;

public interface ReportFileService {
     void saveReportToFile(ReportDTO reportDTO, String fileName);
}
