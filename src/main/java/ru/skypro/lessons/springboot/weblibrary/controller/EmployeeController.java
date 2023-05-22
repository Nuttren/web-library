package ru.skypro.lessons.springboot.weblibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/salary/min")
    public Employee findEmployeeWithMinSalary() {
        List<Employee> allEmployees = employeeService.getAllEmployees();

        if (allEmployees.isEmpty()) {
            return null;
        }

        Employee employeeWithMinSalary = allEmployees.get(0);

        for (Employee employee : allEmployees) {
            if (employee.getSalary() < employeeWithMinSalary.getSalary()) {
                employeeWithMinSalary = employee;
            }
        }
        return employeeWithMinSalary;
    }
    @GetMapping("/employee/salary/max")
    public Employee findEmployeeWithMaxSalary(){
        List<Employee> allEmployees = employeeService.getAllEmployees();

        if (allEmployees.isEmpty()) {
            return null;
        }

        Employee employeeWithMaxSalary = allEmployees.get(0);

        for (Employee employee : allEmployees) {
            if (employee.getSalary() > employeeWithMaxSalary.getSalary()) {
                employeeWithMaxSalary = employee;
            }
        }

        return employeeWithMaxSalary;
    }

    @GetMapping("/employee/salary/sum")
    public double getTotalSalary() {
        List<Employee> allEmployees = employeeService.getAllEmployees();

        double totalSalary = 0;

        for (Employee employee : allEmployees) {
            totalSalary += employee.getSalary();
        }

        return totalSalary;
    }
    @GetMapping("employee/salary/high-salary")
    public List<Employee> getEmployeesWithSalaryAboveAverage() {
        List<Employee> allEmployees = employeeService.getAllEmployees();

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

