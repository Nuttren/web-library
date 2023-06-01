package pojo;

public class Employee {
        private String name;
        private int salary;

        private int id;


    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee(int id, String name, int salary) {
            this.id = id;
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
