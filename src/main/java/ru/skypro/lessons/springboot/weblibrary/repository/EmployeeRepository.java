package ru.skypro.lessons.springboot.weblibrary.repository;

import pojo.Employee;

import java.util.List;

public interface EmployeeRepository {
    public List<Employee> getAllEmployees();
}
