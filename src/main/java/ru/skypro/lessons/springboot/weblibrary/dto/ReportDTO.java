package ru.skypro.lessons.springboot.weblibrary.dto;

import ru.skypro.lessons.springboot.weblibrary.pojo.Report;

import java.io.Serializable;

public class ReportDTO implements Serializable {
    private Long departmentId;

    private String departmentName;

    private int employeesNumber;

    private int maxSalary;

    private int minSalary;

    private int avgSalary;

    private String filePath;

    public static ReportDTO fromReport (Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDepartmentId(report.getDepartmentId());
        reportDTO.setDepartmentName(report.getDepartmentName());
        reportDTO.setEmployeesNumber(report.getEmployeesNumber());
        reportDTO.setMaxSalary(report.getMaxSalary());
        reportDTO.setMinSalary(report.getMinSalary());
        reportDTO.setAvgSalary(report.getAvgSalary());
        reportDTO.setFilePath(report.getFilePath());
        return reportDTO;
    }

    public Report toEmployee() {
        Report report = new Report();
        report.setDepartmentId(this.getDepartmentId());
        report.setDepartmentName(this.getDepartmentName());
        report.setEmployeesNumber(this.getEmployeesNumber());
        report.setMaxSalary(report.getMaxSalary());
        report.setMinSalary(report.getMinSalary());
        report.setAvgSalary(report.getAvgSalary());
        return report;
    }

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
