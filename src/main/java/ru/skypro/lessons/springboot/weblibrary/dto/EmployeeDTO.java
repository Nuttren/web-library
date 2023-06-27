package ru.skypro.lessons.springboot.weblibrary.dto;

import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

public class EmployeeDTO {

    //     Поля для хранения идентификатора, имени и зарплаты сотрудника
    private long id;
    private String name;
    private Integer salary;

    private Long positionId;






    // Метод для преобразования сущности Employee в объект EmployeeDTO
    public static EmployeeDTO fromEmployee (Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setPositionId(employee.getPositionId());
        return employeeDTO;
    }

    // Метод для преобразования объекта EmployeeDTO в сущность Employee
    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.getId());
        employee.setName(this.getName());
        employee.setSalary(this.getSalary());
        return employee;
    }



    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
//     Геттер и сеттер для идентификатора сотрудника
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Геттер и сеттер для имени сотрудника
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Геттер и сеттер для зарплаты сотрудника
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", positionId=" + positionId +
                '}';
    }
}
