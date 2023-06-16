package ru.skypro.lessons.springboot.weblibrary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="student")
public class Student {
    @Id
    @Column(name = "student_id", insertable = false, updatable = false)
    private Long studentId;
    @Column
    private String studentName;

    public Student () {

    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
