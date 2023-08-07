package ru.skypro.lessons.springboot.weblibrary.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.lessons.springboot.weblibrary.config.TestSecurityConfig;
import ru.skypro.lessons.springboot.weblibrary.pojo.Avatar;
import ru.skypro.lessons.springboot.weblibrary.pojo.Student;
import ru.skypro.lessons.springboot.weblibrary.repository.AvatarRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class) // Импорт тестовой конфигурации
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AvatarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    @Test

    public void testUploadAvatar() throws Exception {
        Student student = new Student();
        student.setStudentId(1L);
        studentRepository.save(student);

        byte[] fileContent = "Контет аватара".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "avatar.png", "image/png", fileContent);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/avatar/api/public/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Avatar uploaded successfully"));

        // Проверка, что запись успешно сохранена в базе данных
        List<Avatar> avatars = avatarRepository.findAll();
        Avatar uploadedAvatar = avatars.get(0);
        assertThat(uploadedAvatar.getMediaType()).isEqualTo("image/png");
        assertThat(uploadedAvatar.getFilePath()).isNotNull();
        assertThat(uploadedAvatar.getFileSize()).isEqualTo((long) fileContent.length);
        assertThat(uploadedAvatar.getData()).isEqualTo(fileContent);
    }

    @Test
    public void testGetAvatarById() throws Exception {
        Student student = new Student();
        student.setStudentId(1L);
        studentRepository.save(student);
        Avatar avatar = new Avatar();
        avatar.setStudentId(1);
        avatar.setMediaType("image/jpeg");
        avatar.setData("Контент аватара".getBytes());

        // Сохранение аватара в базе данных
        avatarRepository.save(avatar);

        // Выполнение GET-запроса для получения аватара по его идентификатору и проверка статуса ответа и данных аватара
        mockMvc.perform(MockMvcRequestBuilders.get("/avatar/api/public/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_JPEG));

    }
}
