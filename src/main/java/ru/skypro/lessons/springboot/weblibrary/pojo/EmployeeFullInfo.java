package ru.skypro.lessons.springboot.weblibrary.pojo;

public class EmployeeFullInfo {
        private String name;
        private double salary;
        private String positionName;

        public EmployeeFullInfo(String name, double salary, String positionName) {
            this.name = name;
            this.salary = salary;
            this.positionName = positionName;
        }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        public String getPosition() {
            return positionName;
        }
    }

