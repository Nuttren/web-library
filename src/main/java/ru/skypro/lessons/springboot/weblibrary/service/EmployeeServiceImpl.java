package ru.skypro.lessons.springboot.weblibrary.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void createEmployee(EmployeeDTO employeeDTO) {
        logger.debug("Was invoked method for create employee: {}", employeeDTO);
        Employee employee = employeeDTO.toEmployee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPositionId(employeeDTO.getPositionId());
        employeeRepository.save(employee);
        logger.debug("Employee is :{}", employee);
    }

    @Override
    @Transactional
    public void updateEmployee(long id, EmployeeDTO employeeDTO) {
        logger.debug("Was invoked method for update employee: {}", employeeDTO);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no employee with id = " + id);
                    return new IllegalArgumentException("Employee not found with id: " + id);
                });

        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());


        employeeRepository.save(employee);
        logger.debug("Employee is :{}", employee);
    }


    @Override
    public List<EmployeeDTO> getAllEmployees() {
        logger.debug("Was invoked method to getAllEmployees");
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employee);
            employeeDTOs.add(employeeDTO);
        }
        logger.debug("Employees are :{}", employeeDTOs);
        return employeeDTOs;

    }

    @Override
    public EmployeeDTO getEmployeeById(long id) {
        logger.debug("Was invoked method to getEmployeeById" + id);
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee = employeeOptional.orElseThrow(() -> {
            logger.error("There is no employee with id = " + id);
            return new IncorrectEmployeeIdException((int) id);
        });
        logger.debug("Employees are :{}", employee);
        return EmployeeDTO.fromEmployee(employee);
    }

    @Override
    public void removeEmployee(long id) {
        logger.debug("Was invoked method to removeEmployee" + id);
        employeeRepository.deleteById(id);
        logger.debug("Id of removed employee is :{}", id);
    }

    @Override
    public Employee getEmployeeByName(String name) {
        logger.debug("Was invoked method to getEmployeeByName" + name);
        return employeeRepository.findByName(name).get(0);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByPosition(Position position) {
        logger.debug("Was invoked method to getEmployeeByPosition" + position);
        List<Employee> employees = employeeRepository.findByPosition(position);
        logger.debug("Position is: " + position);
        return employees.stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }


    @Override
    public EmployeeDTO findEmployeeWithMaxSalary() {
        logger.debug("Was invoked method to findEmployeeWithMaxSalary");
        List<Employee> allEmployees = (List<Employee>) employeeRepository.findAll();

        if (allEmployees.isEmpty()) {
            logger.error("There is no any employee");
            throw new IllegalArgumentException("Данные не найдены");
        }

        Employee employeeWithMaxSalary = allEmployees.stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> {
                    logger.error("No data available");
                    return new IllegalArgumentException("Данные не найдены");
                });
        logger.debug("Employee with max salary is {}", employeeWithMaxSalary);
        return EmployeeDTO.fromEmployee(employeeWithMaxSalary);
    }


    @Override
    public EmployeeFullInfo getEmployeeFullInfo(long id) {
        logger.debug("Was invoked method to getEmployeeFullInfo: " + id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is no employee with id = " + id);
                    return new IncorrectEmployeeIdException((int) id);
                });

        Position position = employee.getPosition(); // Получение должности сотрудника

        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo(
                employee.getName(),
                employee.getSalary(),
                position.getPositionName()
        );
        logger.debug("Employees full info is :{}", employeeFullInfo);
        return employeeFullInfo;
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        logger.debug("Was invoked method to  getAllEmployees" + pageable);
        return employeeRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByPage(int page) {
        logger.debug("Was invoked method to getEmployeesByPage: " + page);
        PageRequest pageRequest = PageRequest.of(page, 10); // 10 сотрудников на странице
        Page<Employee> employeePage = employeeRepository.findAll(pageRequest);
        List<EmployeeDTO> employeeDTOList = employeePage.getContent().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
        logger.debug("List by {} is {}: ", page, employeeDTOList);
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
