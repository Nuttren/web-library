package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectEmployeeIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService{

    private static final String REPORT_DIRECTORY = "C:/Reports";
    private final  EmployeeService employeeService;
    private final ReportRepository reportRepository;

    public ReportServiceImpl(EmployeeRepository employeeRepository, EmployeeService employeeService, ReportRepository reportRepository) {
        this.employeeService = employeeService;
        this.reportRepository = reportRepository;
    }


    @Override
    public int generateReport() {
        List<EmployeeDTO> reportDTOList = employeeService.getAllEmployees();
        Report report = new Report();

        int maxSalary = reportDTOList.stream()
                .mapToInt(EmployeeDTO::getSalary)
                .max()
                .orElse(0);

        int minSalary = reportDTOList.stream()
                .mapToInt(EmployeeDTO::getSalary)
                .min()
                .orElse(0);

        int avgSalary = (int) reportDTOList.stream()
                .mapToInt(EmployeeDTO::getSalary)
                .average()
                .orElse(0);

        report.setDepartmentName("Отдел разработки");
        report.setEmployeesNumber(reportDTOList.size());
        report.setMaxSalary(maxSalary);
        report.setMinSalary(minSalary);
        report.setAvgSalary(avgSalary);

        String reportFileName = "report_" + report.getDepartmentId() + ".json";
        String reportFilePath = REPORT_DIRECTORY + "/" + reportFileName;

        // Create and save JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(report);
            FileWriter fileWriter = new FileWriter(reportFilePath);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            // Handle file saving error
            e.printStackTrace();
        }

        report.setFilePath(reportFilePath);
        reportRepository.save(report);
        return report.getDepartmentId().intValue();
    }

    @Override
    public ReportDTO getReportById(long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        Report report = reportOptional.orElseThrow(() -> new IncorrectEmployeeIdException((int) id));
        return ReportDTO.fromReport(report);
    }

    @Override
    public String getReportContentById(Long id) {
        ReportDTO reportDTO = getReportById(id);
        if (reportDTO == null) {
            return null;
        }
        String reportFilePath = reportDTO.getFilePath();

        if (reportFilePath == null || reportFilePath.isEmpty()) {
            return null;
        }

        try {
            Path path = Paths.get(reportFilePath);
            byte[] fileBytes = Files.readAllBytes(path);
            return new String(fileBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
