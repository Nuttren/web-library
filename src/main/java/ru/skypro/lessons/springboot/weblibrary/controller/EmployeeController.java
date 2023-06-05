package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfoDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectEmployeeIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

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
    @GetMapping("employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable long id) {
        try {
            return employeeService.getEmployeeById(id);
        } catch (IncorrectEmployeeIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not found with id: " + id);
        }
    }

    @PostMapping("employee/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.createEmployee(employeeDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error creating employee");
        }
    }

    @PutMapping("employee/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.updateEmployee(id, employeeDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error updating employee: " + t.getMessage());
        }
    }

    @DeleteMapping("employee/delete/{id}")
    public ResponseEntity<?> removeEmployee(@PathVariable long id) {
        try {
            employeeService.removeEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error deleting employee");
        }
    }

    @GetMapping("/employee/salary/max")
    public ResponseEntity<?> findEmployeeWithMaxSalary() {
        try {
            employeeService.findEmployeeWithMaxSalary();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Employee does not exist");
        }

    }

    @GetMapping("/employees/position")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPosition(@RequestParam(value = "position", required = false) String position) {
        List<EmployeeDTO> employees;
        try {
            employees = employeeService.getEmployeesByPosition(position);
            return ResponseEntity.ok(employees);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/employees/{id}/fullInfo")
    public ResponseEntity<?> getEmployeeFullInfo(@PathVariable long id) {
        try {
            EmployeeFullInfoDTO employeeFullInfo = employeeService.getEmployeeFullInfo(id);
            return ResponseEntity.ok(employeeFullInfo);
        }  catch (Throwable t) {
        return ResponseEntity.badRequest().body("Error updating employee: " + t.getMessage());
    }
    }

    @GetMapping("/employees/page")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPage(@RequestParam(defaultValue = "0") int page) {
        int pageSize = 10; // Лимит на количество сотрудников на странице
        Pageable pageable = (Pageable) PageRequest.of(page, pageSize);
        Page<Employee> employeePage = employeeService.getAllEmployees(pageable);
        List<EmployeeDTO> employeeDTO = employeePage.getContent().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTO);
    }
}



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




