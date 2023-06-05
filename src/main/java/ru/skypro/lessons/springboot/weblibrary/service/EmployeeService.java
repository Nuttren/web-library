package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfoDTO;
import org.springframework.data.domain.Page;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.awt.print.Pageable;
import java.util.List;


public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
//
//    Employee findEmployeeWithMinSalary();
//
    EmployeeDTO findEmployeeWithMaxSalary();
//
//    double getTotalSalary();

//    List<Employee> getEmployeesWithSalaryAboveAverage();

    EmployeeDTO getEmployeeById(long id);

    void createEmployee (EmployeeDTO employeeDTO);

    void updateEmployee(long id, EmployeeDTO employeeDTO);

    void removeEmployee(long id);

//    List<Employee> getEmployeesWithHigherSalary(int salary);

    Employee getEmployeeByName(String name);

    List<EmployeeDTO> getEmployeesByPosition(String position);

    EmployeeFullInfoDTO getEmployeeFullInfo(long id);

    Page<Employee> getAllEmployees(Pageable pageable);
}
