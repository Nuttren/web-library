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

        int maxSalary =  reportDTOList.stream()
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
        Report savedReport = reportRepository.save(report);

        String reportFileName = "report_" + savedReport.getDepartmentId() + ".json";
        String reportFilePath = REPORT_DIRECTORY + "/" + reportFileName;

        // Создание и сохранение JSON-файла
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(savedReport);
            FileWriter fileWriter = new FileWriter(reportFilePath);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            // Обработка ошибки сохранения файла
            e.printStackTrace();
        }

        savedReport.setFilePath(reportFilePath);
        reportRepository.save(savedReport);
        return savedReport.getDepartmentId().intValue();
    }

    @Override
    public ReportDTO getReportById(long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        Report report = reportOptional.orElseThrow(() -> new IncorrectEmployeeIdException((int) id));
        return ReportDTO.fromReport(report);
    }



}
