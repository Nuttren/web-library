package ru.skypro.lessons.springboot.weblibrary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.AvatarDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Avatar;
import ru.skypro.lessons.springboot.weblibrary.repository.AvatarRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvatarServiceImplTest {

    @Mock
    private AvatarRepository avatarRepository;

    @InjectMocks
    private AvatarServiceImpl avatarService;


    @Test
    void testUploadAvatar() throws IOException {
        // Создание тестовых данных
        byte[] fileData = "Test file content".getBytes();
        String fileName = "test.jpg";
        long fileSize = 1024;
        String contentType = "image/jpeg";

        MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, contentType, fileData);

        // Настройка поведения заглушек
        when(avatarRepository.save(any(Avatar.class))).thenReturn(new Avatar());

        // Вызов тестируемого метода
        assertDoesNotThrow(() -> avatarService.uploadAvatar(multipartFile));

        // Проверка результатов
        verify(avatarRepository).save(any(Avatar.class));
    }


    @Test
    void testGetAvatarById() {
        // Создание тестовых данных
        long avatarId = 1L;
        Avatar avatar = new Avatar();
        avatar.setAvatarId(avatarId);

        // Настройка поведения заглушки
        when(avatarRepository.findById(avatarId)).thenReturn(Optional.of(avatar));

        // Вызов тестируемого метода
        AvatarDTO result = avatarService.getAvatarById(avatarId);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(avatarId, result.getAvatarId());

        // Проверка вызовов заглушки
        verify(avatarRepository).findById(avatarId);
    }


    @Test
    void testGetAvatarImageById() {
        // Создание тестовых данных
        long avatarId = 1L;
        byte[] imageData = new byte[]{1, 2, 3};

        // Настройка поведения заглушки
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setData(imageData);
        when(avatarRepository.findById(anyLong())).thenReturn(Optional.ofNullable(avatarDTO.toAvatar()));

        // Вызов тестируемого метода
        ResponseEntity<byte[]> result = avatarService.getAvatarImageById(avatarId);

        // Проверка результатов
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertArrayEquals(imageData, result.getBody());
        HttpHeaders headers = result.getHeaders();
        Assertions.assertNotNull(headers);
        Assertions.assertEquals(MediaType.IMAGE_JPEG, headers.getContentType());

        // Проверка вызовов заглушки
        verify(avatarRepository).findById(avatarId);
    }

}