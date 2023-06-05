package ru.skypro.lessons.springboot.weblibrary.service;

import dto.EmployeeDTO;
import org.springframework.stereotype.Component;
import pojo.Employee;

import java.util.List;


public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
//
//    Employee findEmployeeWithMinSalary();
//
//    Employee findEmployeeWithMaxSalary();
//
//    double getTotalSalary();

//    List<Employee> getEmployeesWithSalaryAboveAverage();

    Employee getEmployeeById(Integer id);

    void createEmployee (Employee employee);

//    void updateEmployee(int id, Employee employee);

    void removeEmployee(Integer id);

//    List<Employee> getEmployeesWithHigherSalary(int salary);

    Employee getEmployeeByName(String name);
}
