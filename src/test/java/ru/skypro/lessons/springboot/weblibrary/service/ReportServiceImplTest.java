package ru.skypro.lessons.springboot.weblibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void testGenerateReport() {
        // Создание тестовых данных
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setSalary(5000);
        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setSalary(7000);
        EmployeeDTO employee3 = new EmployeeDTO();
        employee3.setSalary(6000);
        employeeDTOList.add(employee1);
        employeeDTOList.add(employee2);
        employeeDTOList.add(employee3);

        Report savedReport = new Report();
        savedReport.setDepartmentId(1L);
        savedReport.setDepartmentName("Отдел разработки");
        savedReport.setEmployeesNumber(employeeDTOList.size());
        savedReport.setMaxSalary(7000);
        savedReport.setMinSalary(5000);
        savedReport.setAvgSalary(6000);

        // Настройка поведения заглушек
        when(employeeService.getAllEmployees()).thenReturn(employeeDTOList);
        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> {
            Report report = invocation.getArgument(0);
            report.setDepartmentId(savedReport.getDepartmentId());
            return report;
        });

        // Вызов тестируемого метода
        int departmentId = reportService.generateReport();

        // Проверка результатов
        assertEquals(savedReport.getDepartmentId().intValue(), departmentId);

        // Проверка вызовов методов
        verify(employeeService).getAllEmployees();
        verify(reportRepository).save(any(Report.class));
    }

    @Test
    void testGetReportById() {
        // Создание тестовых данных
        long reportId = 1L;
        Report report = new Report();
        report.setDepartmentId(reportId);
        report.setDepartmentName("Отдел разработки");
        // Другие установки значений для объекта report

        // Настройка поведения заглушки reportRepository
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        // Вызов тестируемого метода
        ReportDTO result = reportService.getReportById(reportId);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(report.getDepartmentId(), result.getDepartmentId());
        assertEquals(report.getDepartmentName(), result.getDepartmentName());
        // Проверка других полей объекта ReportDTO

        // Проверка вызовов методов
        verify(reportRepository).findById(reportId);
    }

}