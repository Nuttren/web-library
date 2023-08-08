
package ru.skypro.lessons.springboot.weblibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.lessons.springboot.weblibrary.config.TestSecurityConfig;
import ru.skypro.lessons.springboot.weblibrary.pojo.Report;
import ru.skypro.lessons.springboot.weblibrary.repository.ReportRepository;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class) // Импорт тестовой конфигурации
@Testcontainers
public class ReportControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private EntityManager entityManager;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @Transactional
    @Rollback(false) // Отключаем откат транзакции для сохранения данных в базе данных
    public void testGenerateReport() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/report/"))
                .andExpect(status().isOk())
                .andReturn();

        // Получаем возвращенный идентификатор отчета
        int reportId = Integer.parseInt(mvcResult.getResponse().getContentAsString());

        // Принудительно синхронизируем с базой данных
        entityManager.flush();

        // Проверяем, что идентификатор отчета не равен нулю и положительный
        assertTrue(reportId > 0);
    }

    @Test
    public void testGetReportById() throws Exception {
        // Создание объекта Report с данными отчета
        Report report = new Report();
        report.setDepartmentId(20L);
        report.setDepartmentName("Department A");
        report.setEmployeesNumber(10);
        report.setMaxSalary(1000);
        report.setMinSalary(500);
        report.setAvgSalary(800);
        report.setFilePath("C:/Reports/report_20.json");

        reportRepository.save(report);

        Long nonExistentId = 999L;

        mockMvc.perform(get("/report/api/public/" + nonExistentId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Incorrect employee ID: 999")));
    }
}

