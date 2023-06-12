package ru.skypro.lessons.springboot.weblibrary.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectEmployeeIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.toEmployee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPositionId(employeeDTO.getPositionId());
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(long id, EmployeeDTO employeeDTO) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));


        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());


        employeeRepository.save(employee);
    }


    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employee);
            employeeDTOs.add(employeeDTO);
        }

        return employeeDTOs;
    }

    @Override
    public EmployeeDTO getEmployeeById(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee = employeeOptional.orElseThrow(() -> new IncorrectEmployeeIdException((int) id));
        return EmployeeDTO.fromEmployee(employee);
    }

    @Override
    public void removeEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name).get(0);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByPosition(Position position) {
        List<Employee> employees = employeeRepository.findByPosition(position);
        return employees.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }


    @Override
    public EmployeeDTO findEmployeeWithMaxSalary() {
        List<Employee> allEmployees = (List<Employee>) employeeRepository.findAll();

        if (allEmployees.isEmpty()) {
            throw new IllegalArgumentException("Данные не найдены");
        }

        Employee employeeWithMaxSalary = allEmployees.stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new IllegalArgumentException("Данные не найдены"));

        return EmployeeDTO.fromEmployee(employeeWithMaxSalary);
    }


    @Override
    public EmployeeFullInfo getEmployeeFullInfo(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IncorrectEmployeeIdException((int) id));

        Position position = employee.getPosition(); // Получение должности сотрудника

        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo(
                employee.getName(),
                employee.getSalary(),
                position.getPositionName()
        );

        return employeeFullInfo;
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByPage(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10); // 10 сотрудников на странице
        Page<Employee> employeePage = employeeRepository.findAll(pageRequest);
        List<EmployeeDTO> employeeDTOList = employeePage.getContent().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
        return employeeDTOList;
    }

    @Override
    public void saveAllEmployees(List<EmployeeDTO> employees) {
        List<Employee> employeesList = employees.stream()
                .map(EmployeeDTO::toEmployee)
                .collect(Collectors.toList());

        employeeRepository.saveAll(employeesList);
    }


}
