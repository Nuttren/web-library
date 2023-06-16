package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.AvatarDTO;

import java.io.IOException;

public interface AvatarService {

    String saveFile(byte[] data, String fileName) throws IOException;

    void uploadAvatar(MultipartFile file) throws IOException;

    AvatarDTO getAvatarById(long id);

    ResponseEntity<byte[]> getAvatarImageById(long id);

    ResponseEntity<Resource> getAvatarImageFromFile(String fileName);
}
