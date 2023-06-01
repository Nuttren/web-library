package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Component;
import pojo.Employee;

import java.util.List;


public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee findEmployeeWithMinSalary();

    Employee findEmployeeWithMaxSalary();

    double getTotalSalary();

    List<Employee> getEmployeesWithSalaryAboveAverage();
}
