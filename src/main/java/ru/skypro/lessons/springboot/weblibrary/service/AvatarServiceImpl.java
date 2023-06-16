package ru.skypro.lessons.springboot.weblibrary.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.AvatarDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectAvatarIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.Avatar;
import ru.skypro.lessons.springboot.weblibrary.repository.AvatarRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AvatarServiceImpl implements AvatarService{
    private static final String UPLOAD_DIRECTORY = "C:/Media";
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    @Override
    public String saveFile(byte[] data, String fileName) throws IOException {
        String filePath = UPLOAD_DIRECTORY + "/" + fileName;
        Path path = Paths.get(filePath);
        Files.write(path, data);
        return filePath;
    }

    @Override
    public void uploadAvatar(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] data = file.getBytes();
            String fileName = file.getOriginalFilename();

            // Сохранение файла на локальный диск
            String filePath = saveFile(data, fileName);

            // Сохранение пути к файлу в базе данных
            Avatar avatar = new Avatar();
            avatar.setFilePath(filePath);
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());
            avatar.setStudentId(1);


            avatarRepository.save(avatar);
        } else {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
    }

    @Override
    public AvatarDTO getAvatarById(long id) {
        Optional<Avatar> avatarOptional = avatarRepository.findById(id);
        Avatar avatar = avatarOptional.orElseThrow(() -> new IncorrectAvatarIdException(id));
        return AvatarDTO.fromAvatar(avatar);
    }
    @Override
    public ResponseEntity<byte[]> getAvatarImageById(long id) {
        AvatarDTO avatarDTO = getAvatarById(id);
        if (avatarDTO == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] imageData = avatarDTO.getData();
        if (imageData == null || imageData.length == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ResponseEntity.ok().headers(headers).body(imageData);
    }

    @Override
    public ResponseEntity<Resource> getAvatarImageFromFile(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    }



