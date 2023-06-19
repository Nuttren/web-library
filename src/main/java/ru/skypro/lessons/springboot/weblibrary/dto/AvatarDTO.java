package ru.skypro.lessons.springboot.weblibrary.dto;

import ru.skypro.lessons.springboot.weblibrary.pojo.Avatar;

public class AvatarDTO {

    private Long avatarId;

    private String filePath;

    private Long fileSize;

    private String MediaType;

    private int studentId;

    private byte[] data;

    public static AvatarDTO fromAvatar(Avatar avatar) {
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setAvatarId(avatar.getAvatarId());
        avatarDTO.setFilePath(avatar.getFilePath());
        avatarDTO.setFileSize(avatar.getFileSize());
        avatarDTO.setMediaType(avatar.getMediaType());
        avatarDTO.setData(avatar.getData());
        return avatarDTO;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
