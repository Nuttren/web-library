package dto;

public class EmployeeFullInfoDTO {
        private String name;
        private double salary;
        private String positionName;

        public EmployeeFullInfoDTO(String name, double salary, String positionName) {
            this.name = name;
            this.salary = salary;
            this.positionName = positionName;
        }

        public String getPositionName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        public String getPosition() {
            return positionName;
        }
    }

