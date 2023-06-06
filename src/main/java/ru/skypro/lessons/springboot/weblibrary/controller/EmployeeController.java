package ru.skypro.lessons.springboot.weblibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectEmployeeIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee/list")
    public List<EmployeeDTO> getAllEmployees(){
            return employeeService.getAllEmployees();
    }

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
            EmployeeDTO employeeWithMaxSalary = employeeService.findEmployeeWithMaxSalary();
            return ResponseEntity.ok(employeeWithMaxSalary);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Employee does not exist");
        }

    }

    @GetMapping("/employees/position")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPosition(@RequestParam(value = "position", required = false) Position position) {
        List<EmployeeDTO> employees;
        if (StringUtils.hasText((CharSequence) position)) {
            employees = employeeService.getEmployeesByPosition(position);
        } else {
            employees = employeeService.getAllEmployees();
        }
        return ResponseEntity.ok(employees);
        }


    @GetMapping("/employees/{id}/fullInfo")
    public ResponseEntity<?> getEmployeeFullInfo(@PathVariable long id) {
        try {
            EmployeeFullInfo employeeFullInfo = employeeService.getEmployeeFullInfo(id);
            return ResponseEntity.ok(employeeFullInfo);
        }  catch (Throwable t) {
        return ResponseEntity.badRequest().body("Error updating employee: " + t.getMessage());
    }
    }

    @GetMapping("/employees/page")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByPage(page);
        return ResponseEntity.ok(employees);
    }
}








