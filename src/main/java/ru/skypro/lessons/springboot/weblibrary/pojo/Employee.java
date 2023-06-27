package ru.skypro.lessons.springboot.weblibrary.pojo;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Column (name = "name")
        private String name;

    @Column (name = "salary")
        private int salary;
    @Id
    @Column (name = "id")
        private Long id;


    @Column(name = "position_id")
    private Long positionId;

    @Column (name = "department_id")
    private Long departmentId;



    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn (name = "position_id", insertable=false, updatable=false)
    private Position position;

    public Position getPosition() {
        return position;
    }

    public Employee() {

    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee(String name, int salary, Long positionId) {
            this.name = name;
            this.salary = salary;
            this.positionId = positionId;
        }
        public long getId() {return id;}
        public String getName() {
            return name;
        }

        public int getSalary() {
            return salary;
        }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", id=" + id +
                ", positionId=" + positionId +
                ", departmentId=" + departmentId +
                ", position=" + position +
                '}';
    }
}
