package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.data.domain.Page;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;

import java.awt.print.Pageable;
import java.util.List;


public interface EmployeeService {

    EmployeeDTO findEmployeeWithMaxSalary();

    EmployeeDTO getEmployeeById(long id);

    void createEmployee (EmployeeDTO employeeDTO);

    void updateEmployee(long id, EmployeeDTO employeeDTO);

    void removeEmployee(long id);

//    List<Employee> getEmployeesWithHigherSalary(int salary);

    Employee getEmployeeByName(String name);

    List<EmployeeDTO> getEmployeesByPosition(Position position);

    List<EmployeeDTO> getAllEmployees();

    EmployeeFullInfo getEmployeeFullInfo(long id);

    Page<Employee> getAllEmployees(Pageable pageable);

    List<EmployeeDTO> getEmployeesByPage(int page);
}
