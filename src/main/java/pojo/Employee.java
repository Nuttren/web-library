package pojo;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

    public Employee() {

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }
        public int getId() {return id;}
        public String getName() {
            return name;
        }

        public int getSalary() {
            return salary;
        }
    }
