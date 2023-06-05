package ru.skypro.lessons.springboot.weblibrary.service;


import dto.EmployeeDTO;
import exeption.IncorrectEmployeeIdException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service

public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }


    @Override
    public List<EmployeeDTO> getAllEmployees() {
        Stream<Employee> employeeStream = StreamSupport.stream(employeeRepository.findAll().spliterator(), false);
        // Получаем список сотрудников из репозитория,
        // Преобразуем их в DTO и собираем в список
        return employeeStream
                .map(EmployeeDTO::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        // Используем метод findById() репозитория для получения сотрудника по id
        // Возвращает Optional<Employee>,
        // который может содержать сотрудника или быть пустым
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        // Если сотрудник найден, возвращаем его
        // Иначе выбрасываем исключение с указанием некорректного id
        return employeeOptional.orElseThrow(() -> new IncorrectEmployeeIdException(id));
    }

    @Override
    public void removeEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name).get(0);
    }


//    @Override
//    public List<Employee> getAllEmployees() {
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
