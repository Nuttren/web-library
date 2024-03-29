package ru.skypro.lessons.springboot.weblibrary.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectEmployeeIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeServiceImpl;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @GetMapping("public/list")
    public List<EmployeeDTO> getAllEmployees() {
        logger.debug("Data returned from employeeService.getAllEmployees(): {}", employeeService.getAllEmployees());
        return employeeService.getAllEmployees();
    }

    @GetMapping("public/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable long id) {
        try {
            return employeeService.getEmployeeById(id);
        } catch (IncorrectEmployeeIdException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not found with id: " + id);
        }
    }

    @PostMapping("admin/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.createEmployee(employeeDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error creating employee");
        }
    }

    @PutMapping("admin/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeService.updateEmployee(id, employeeDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error updating employee: " + t.getMessage());
        }
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<?> removeEmployee(@PathVariable long id) {
        try {
            employeeService.removeEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error deleting employee");
        }
    }

    @GetMapping("admin/salary/max")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findEmployeeWithMaxSalary() {
        try {
            EmployeeDTO employeeWithMaxSalary = employeeService.findEmployeeWithMaxSalary();
            return ResponseEntity.ok(employeeWithMaxSalary);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Employee does not exist");
        }

    }

    @GetMapping("user/position")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPosition(@RequestParam(value = "position", required = false) Position position) {
        List<EmployeeDTO> employees;
        if (StringUtils.hasText((CharSequence) position)) {
            employees = employeeService.getEmployeesByPosition(position);
        } else {
            employees = employeeService.getAllEmployees();
        }
        return ResponseEntity.ok(employees);
    }


    @GetMapping("user/employees/{id}/fullInfo")
    public ResponseEntity<?> getEmployeeFullInfo(@PathVariable long id) {
        try {
            EmployeeFullInfo employeeFullInfo = employeeService.getEmployeeFullInfo(id);
            return ResponseEntity.ok(employeeFullInfo);
        } catch (Throwable t) {
            return ResponseEntity.badRequest().body("Error updating employee: " + t.getMessage());
        }
    }

    @GetMapping("user/employees/page")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByPage(page);
        return ResponseEntity.ok(employees);
    }

    @PostMapping(value = "public/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadEmployees(@RequestParam("file") MultipartFile file) {
        try {
            // Читаем содержимое файла и преобразуем его в список объектов EmployeeDTO
            ObjectMapper objectMapper = new ObjectMapper();
            List<EmployeeDTO> employees = objectMapper.readValue(file.getInputStream(), new TypeReference<>() {
            });

            // Сохраняем сотрудников в базе данных
            employeeService.saveAllEmployees(employees);

            return ResponseEntity.ok("Employees uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload employees: " + e.getMessage());
        }
    }

}








