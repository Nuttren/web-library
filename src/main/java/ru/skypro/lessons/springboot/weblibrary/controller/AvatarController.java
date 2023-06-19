package ru.skypro.lessons.springboot.weblibrary.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @PostMapping(value ="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            avatarService.uploadAvatar(file);
            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload avatar");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getAvatarById(@PathVariable Long id) {
        return avatarService.getAvatarImageById(id);
    }

    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<Resource> getAvatarImageFromFile(@PathVariable String fileName) {
        return avatarService.getAvatarImageFromFile(fileName);
    }
}
