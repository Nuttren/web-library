package ru.skypro.lessons.springboot.weblibrary.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.util.List;


public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
//    public List<Employee> findAllEmployees();
//
//    void createEmployee (Employee employee);
//
//    void removeEmployee (int id);
//
//    List<Employee> getEmployeesWithHigherSalary(int salary);

    List<Employee> findByName(String name);

    List<Employee> findByPosition(String position);



}
