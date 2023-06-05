package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pojo.Employee;

import java.util.List;


public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
//    public List<Employee> findAllEmployees();
//
//    void createEmployee (Employee employee);
//
//    void removeEmployee (int id);
//
//    List<Employee> getEmployeesWithHigherSalary(int salary);

    List<Employee> findByName(String name);
}
