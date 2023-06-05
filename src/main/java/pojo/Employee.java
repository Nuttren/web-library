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
        private long id;

    @Basic
    @Column(name = "position_id", insertable=false, updatable=false)
    private Integer positionId;

    @ManyToOne
    @JoinColumn (name = "position_id", insertable=false, updatable=false)
    private Position position;

    public Position getPosition() {
        return position;
    }

    public Employee() {

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

    public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }
        public long getId() {return id;}
        public String getName() {
            return name;
        }

        public int getSalary() {
            return salary;
        }
    }
