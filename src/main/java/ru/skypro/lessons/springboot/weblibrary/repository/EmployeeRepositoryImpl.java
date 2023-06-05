package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.stereotype.Repository;
import pojo.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private List<Employee> employeeList = new ArrayList<>(List.of(
            new Employee(0, "Катя", 180_000),
            new Employee(1, "Дима", 102_000),
            new Employee(2, "Олег", 80_000),
            new Employee(3, "Вика", 165_000)));

    @Override
    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    @Override
    public void createEmployee(Employee employee) {
        employeeList.add(employee);
    }

    @Override
    public void removeEmployee(int id) {
    employeeList.remove(id);
    }

    public List<Employee> getEmployeesWithHigherSalary(int salary) {
        return employeeList.stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toUnmodifiableList());
    }

}


