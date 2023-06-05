package ru.skypro.lessons.springboot.weblibrary.repository;

import dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pojo.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {



//    private List<Employee> employeeList = new ArrayList<> ();

//    @Override
//    public List<Employee> getAllEmployees() {
//        return employeeList;
//    }
//
//    @Override
//    public void createEmployee(Employee employee) {
//        employeeList.add(employee);
//    }
//
//    @Override
//    public void removeEmployee(int id) {
//    employeeList.remove(id);
//    }
//
//    @Override
//    public List<Employee> getEmployeesWithHigherSalary(int salary) {
//        return employeeList.stream()
//                .filter(employee -> employee.getSalary() > salary)
//                .collect(Collectors.toUnmodifiableList());
//    }


    @Override
    public <S extends Employee> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Employee> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Iterable<Employee> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Employee entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Employee> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Employee> findByName(String name) {
        return null;
    }

    @Override
    public List<Employee> findByPosition(String position) {
        return null;
    }


    @Override
    public Iterable<Employee> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return null;
    }
}


