package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

//    @GetMapping("/employee")
//    public List<Employee> getAllEmployees() {
//        return employeeService.getAllEmployees();
//    }
//
//    @GetMapping("/employee/salary/min")
//    public Employee findEmployeeWithMinSalary() {
//        List<Employee> allEmployees = employeeService.getAllEmployees();
//
//        if (allEmployees.isEmpty()) {
//            throw new IllegalArgumentException("Данные не найдены");
//        }
//
//        Employee employeeWithMinSalary = allEmployees.get(0);
//
//        for (Employee employee : allEmployees) {
//            if (employee.getSalary() < employeeWithMinSalary.getSalary()) {
//                employeeWithMinSalary = employee;
//            }
//        }
//        return employeeWithMinSalary;
//    }
//
//    @GetMapping("/employee/salary/max")
//    public Employee findEmployeeWithMaxSalary() {
//        List<Employee> allEmployees = employeeService.getAllEmployees();
//
//        if (allEmployees.isEmpty()) {
//            throw new IllegalArgumentException("Данные не найдены");
//        }
//
//        Employee employeeWithMaxSalary = allEmployees.get(0);
//
//        for (Employee employee : allEmployees) {
//            if (employee.getSalary() > employeeWithMaxSalary.getSalary()) {
//                employeeWithMaxSalary = employee;
//            }
//        }
//
//        return employeeWithMaxSalary;
//    }
//
//    @GetMapping("/employee/salary/sum")
//    public double getTotalSalary() {
//        List<Employee> allEmployees = employeeService.getAllEmployees();
//
//        double totalSalary = 0;
//
//        for (Employee employee : allEmployees) {
//            totalSalary += employee.getSalary();
//        }
//
//        return totalSalary;
//    }
//
//    @GetMapping("employee/salary/high-salary")
//    public List<Employee> getEmployeesWithSalaryAboveAverage() {
//        List<Employee> allEmployees = employeeService.getAllEmployees();
//
//        double totalSalary = 0;
//        int count = 0;
//
//        for (Employee employee : allEmployees) {
//            totalSalary += employee.getSalary();
//            count++;
//        }
//
//        double averageSalary = totalSalary / count;
//
//        List<Employee> employeesAboveAverage = new ArrayList<>();
//
//        for (Employee employee : allEmployees) {
//            if (employee.getSalary() > averageSalary) {
//                employeesAboveAverage.add(employee);
//            }
//        }
//
//        return employeesAboveAverage;
//    }
//
//    @GetMapping("employee/{id}")
//    public Employee getEmployeeById(@PathVariable int id) {
//        try {
//            return employeeService.getAllEmployees().get(id);
//        } catch (Throwable t) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not found with id: " + id);
//        }
//    }
//
//    @PostMapping("employee/create")
//    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
//        try {
//            employeeService.createEmployee(employee);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Throwable t) {
//            return ResponseEntity.badRequest().body("Error creating employee");
//        }
//    }
//
//    @PutMapping("employee/update/{id}")
//    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
//        try {
//            employeeService.updateEmployee(id, employee);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Throwable t) {
//            return ResponseEntity.badRequest().body("Error updating employee");
//        }
//    }
//
//    @DeleteMapping("employee/delete/{id}")
//    public ResponseEntity<?> removeEmployee(@PathVariable int id) {
//        try {
//            employeeService.removeEmployee(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Throwable t) {
//            return ResponseEntity.badRequest().body("Error deleting employee");
//        }
//    }
//
//    @GetMapping("/employees/salaryHigherThan")
//    public ResponseEntity<List<Employee>> getEmployeesWithHigherSalary(@RequestParam("salary") int salary) {
//        try {
//        List<Employee> employees = employeeService.getEmployeesWithHigherSalary(salary);
//        return ResponseEntity.ok(employees);
//        } catch (Throwable t) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            employeeService.createEmployee(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error creating employee");
        }
    }
}

