package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/generate")
    public ResponseEntity<Integer> generateReport() {
        try {
            int reportId = reportService.generateReport();
            return ResponseEntity.ok(reportId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<String> getReportById(@PathVariable Long id) {
        ReportDTO reportDTO = reportService.getReportById(id);
        if (reportDTO == null) {
            return ResponseEntity.notFound().build();
        }
        String reportFilePath = reportDTO.getFilePath();

        if (reportFilePath == null || reportFilePath.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

//         Read the file content
        try {
            Path path = Paths.get(reportFilePath);
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            // Handle file reading error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
