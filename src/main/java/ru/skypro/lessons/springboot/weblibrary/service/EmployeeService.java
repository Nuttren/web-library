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

    Employee getEmployeeById(int id);

    void createEmployee (Employee employee);

    void updateEmployee(int id, Employee employee);

    void removeEmployee(int id);

    List<Employee> getEmployeesWithHigherSalary(int salary);

}
