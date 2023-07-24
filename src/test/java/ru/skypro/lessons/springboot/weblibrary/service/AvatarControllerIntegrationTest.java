package ru.skypro.lessons.springboot.weblibrary.service;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.lessons.springboot.weblibrary.config.TestSecurityConfig;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class) // Импорт тестовой конфигурации

public class AvatarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvatarServiceImpl avatarService;

    @Test
    public void testUploadAvatar() throws Exception {
        // Create a sample file to upload
        byte[] fileContent = "Контет аватара".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "avatar.png", "image/png", fileContent);

        // Укажите ожидаемое поведение мок-сервиса при вызове метода uploadAvatar
        Mockito.doNothing().when(avatarService).uploadAvatar(file);

        // Perform the POST request to upload the file
        mockMvc.perform(MockMvcRequestBuilders.multipart("/avatar/api/public/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Avatar uploaded successfully"));
    }

    @Test
    public void testGetAvatarById() throws Exception {
        // Устанавливаем значения, которые вернет сервис при вызове метода
        byte[] avatarImage = new byte[]{0x12, 0x34, 0x56, 0x78};
        when(avatarService.getAvatarImageById(1L)).thenReturn(new ResponseEntity<>(avatarImage, HttpStatus.OK));

        // Выполняем GET-запрос
        mockMvc.perform(get("/avatar/api/public/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM)) // Указываем тип контента как application/octet-stream
                .andExpect(content().bytes(avatarImage));
    }

    @Test
    public void testGetAvatarImageFromFile() throws Exception {
        // Фиктивные данные изображения
        byte[] avatarImageBytes = new byte[]{0x12, 0x34, 0x56, 0x78};

        // Здесь, avatarFileName представляет имя файла, которое вы хотите использовать в тесте
        String avatarFileName = "avatar.jpg";

        when(avatarService.getAvatarImageFromFile(avatarFileName))
                .thenReturn(new ResponseEntity<>(new ByteArrayResource(avatarImageBytes), getResponseHeadersForFile(avatarFileName), HttpStatus.OK));

        mockMvc.perform(get("/avatar/api/public/file/" + avatarFileName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG)) // Указываем тип контента как image/jpeg (замените на соответствующий, если это другой тип файла)
                .andExpect(content().bytes(avatarImageBytes));
    }

    private HttpHeaders getResponseHeadersForFile(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        // Здесь устанавливаем правильный тип контента на основе расширения файла (в данном случае, для изображения JPEG)
        headers.setContentType(MediaType.IMAGE_JPEG);
        // Добавляем заголовок Content-Disposition для указания имени файла, если нужно
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
        return headers;
    }
}
