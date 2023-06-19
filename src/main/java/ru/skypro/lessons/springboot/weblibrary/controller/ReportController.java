package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.weblibrary.service.ReportService;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping()
    public ResponseEntity<Integer> generateReport() {
        try {
            int reportId = reportService.generateReport();
            return ResponseEntity.ok(reportId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/public/{id}")
    public ResponseEntity<String> getReportById(@PathVariable Long id) {
        String reportContent = reportService.getReportContentById(id);
        if (reportContent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportContent);
        }
    }

