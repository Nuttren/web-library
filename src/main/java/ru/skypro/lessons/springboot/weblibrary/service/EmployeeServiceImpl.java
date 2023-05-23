package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.stereotype.Service;
import pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

@Service

public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee findEmployeeWithMinSalary() {
        List<Employee> allEmployees = employeeRepository.getAllEmployees();

        if (allEmployees.isEmpty()) {
            throw new IllegalArgumentException ("Данные не найдены");
        }

        Employee employeeWithMinSalary = allEmployees.get(0);

        for (Employee employee : allEmployees) {
            if (employee.getSalary() < employeeWithMinSalary.getSalary()) {
                employeeWithMinSalary = employee;
            }
        }

        return employeeWithMinSalary;
    }

    @Override
    public Employee findEmployeeWithMaxSalary() {
        List<Employee> allEmployees = employeeRepository.getAllEmployees();

        if (allEmployees.isEmpty()) {
            throw new IllegalArgumentException ("Данные не найдены");
        }

        Employee employeeWithMaxSalary = allEmployees.get(0);

        for (Employee employee : allEmployees) {
            if (employee.getSalary() > employeeWithMaxSalary.getSalary()) {
                employeeWithMaxSalary = employee;
            }
        }

        return employeeWithMaxSalary;
    }

    @Override
    public double getTotalSalary() {
        List<Employee> allEmployees = employeeRepository.getAllEmployees();

        double totalSalary = 0;

        for (Employee employee : allEmployees) {
            totalSalary += employee.getSalary();
        }

        return totalSalary;
    }

    @Override
    public List<Employee> getEmployeesWithSalaryAboveAverage() {
        List<Employee> allEmployees = employeeRepository.getAllEmployees();

        double totalSalary = 0;
        int count = 0;

        for (Employee employee : allEmployees) {
            totalSalary += employee.getSalary();
            count++;
        }

        double averageSalary = totalSalary / count;

        List<Employee> employeesAboveAverage = new ArrayList<>();

        for (Employee employee : allEmployees) {
            if (employee.getSalary() > averageSalary) {
                employeesAboveAverage.add(employee);
            }
        }

        return employeesAboveAverage;
    }

}
