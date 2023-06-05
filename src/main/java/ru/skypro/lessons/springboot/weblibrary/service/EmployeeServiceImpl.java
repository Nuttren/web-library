package ru.skypro.lessons.springboot.weblibrary.service;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeFullInfoDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectEmployeeIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.awt.print.Pageable;
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
    public void createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());

        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(long id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(employeeDTO.getName());
            employee.setSalary(employeeDTO.getSalary());
        }
    }


    @Override
    public List<EmployeeDTO> getAllEmployees() {
        // Получаем список сотрудников из репозитория,
        // Преобразуем их в DTO и собираем в список
        return employeeRepository.findAllEmployees().stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
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
    public List<EmployeeDTO> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position).stream()
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeFullInfoDTO getEmployeeFullInfo(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IncorrectEmployeeIdException((int) id));

        Position position = employee.getPosition(); // Получение должности сотрудника

        // Создание объекта EmployeeFullInfoDTO, который содержит имя, зарплату и название должности сотрудника
        EmployeeFullInfoDTO employeeFullInfo = new EmployeeFullInfoDTO(
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


//    @Override
//    public List<Employee> getAllEmployeesOld() {
//        return employeeRepository.getAllEmployees();
//    }
//
//    @Override
//    public Employee findEmployeeWithMinSalary() {
//        List<Employee> allEmployees = employeeRepository.getAllEmployees();
//
//        if (allEmployees.isEmpty()) {
//            throw new IllegalArgumentException ("Данные не найдены");
//        }
//
//        Employee employeeWithMinSalary = allEmployees.get(0);
//
//        for (Employee employee : allEmployees) {
//            if (employee.getSalary() < employeeWithMinSalary.getSalary()) {
//                employeeWithMinSalary = employee;
//            }
//        }
//
//        return employeeWithMinSalary;
//    }
//
//    @Override
//    public Employee findEmployeeWithMaxSalary() {
//        List<Employee> allEmployees = employeeRepository.getAllEmployees();
//
//        if (allEmployees.isEmpty()) {
//            throw new IllegalArgumentException ("Данные не найдены");
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
//    @Override
//    public double getTotalSalary() {
//        List<Employee> allEmployees = employeeRepository.getAllEmployees();
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
//    @Override
//    public List<Employee> getEmployeesWithSalaryAboveAverage() {
//        List<Employee> allEmployees = employeeRepository.getAllEmployees();
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
//    @Override
//    public Employee getEmployeeById(int id) {
//
//        return employeeRepository.getAllEmployees().get(id);
//    }
//
//    @Override
//    public void createEmployee(Employee employee) {
//        employeeRepository.save(employee);
//    }
//
//    @Override
//    public void updateEmployee(int id, Employee employee) {
//        Employee existingEmployee = employeeRepository.getAllEmployees().get(id);
//        if (existingEmployee != null) {
//            existingEmployee.setName(employee.getName());
//            existingEmployee.setSalary(employee.getSalary());
//        }
//    }
//
//    @Override
//    public void removeEmployee(int id) {
//        employeeRepository.removeEmployee(id);
//    }
//
//    @Override
//    public List<Employee> getEmployeesWithHigherSalary(int salary) {
//        return employeeRepository.getEmployeesWithHigherSalary(salary);
//    }

}
