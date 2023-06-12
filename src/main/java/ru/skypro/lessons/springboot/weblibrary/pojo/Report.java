package ru.skypro.lessons.springboot.weblibrary.pojo;



import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "report")

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", insertable = false, updatable = false)

    private Long departmentId;
    @Column(name = "department_name")

    private String departmentName;
    @Column (name = "employee_count")

    private int employeesNumber;
    @Column (name = "max_salary")

    private int maxSalary;
    @Column (name = "min_salary")

    private int minSalary;
    @Column (name = "avg_salary")
    private int avgSalary;

    @Column (name = "file_path")
    private String filePath;
    @OneToMany
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", insertable = false, updatable = false)
    private List<Employee> employee;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(int avgSalary) {
        this.avgSalary = avgSalary;
    }


}
