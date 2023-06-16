package ru.skypro.lessons.springboot.weblibrary.pojo;

import javax.persistence.*;

@Entity
@Table (name = "avatar")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", insertable = false, updatable = false)
    private Long avatarId;
    @Column
    private String filePath;
    @Column
    private Long fileSize;
    @Column
    private String MediaType;
    @Column (columnDefinition = "bytea")
    private byte[] data;
    @OneToOne
    @JoinColumn (name = "student_id", insertable=false, updatable=false)
    private Student student;

    @Column (name = "student_id")
    private int studentId;


    public Avatar() {
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return MediaType;
    }

    public void setMediaType(String mediaType) {
        MediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
