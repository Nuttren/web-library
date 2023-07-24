
package ru.skypro.lessons.springboot.weblibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.weblibrary.config.TestSecurityConfig;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class) // Импорт тестовой конфигурации
public class ReportControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportServiceImpl reportService;

    @MockBean
    private EmployeeServiceImpl employeeService; // Импортируем реальный EmployeeService

    @Test
    public void testGenerateReport() throws Exception {
        int departmentId = 1;

        // Мокируем поведение вашего сервиса с помощью thenReturn()
        when(reportService.generateReport()).thenReturn(departmentId);

        // Выполняем POST-запрос
        mockMvc.perform(post("/report/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(departmentId))); // Проверяем, что возвращаемое значение равно departmentId

        // Проверяем, что ваш сервис был вызван с нужными параметрами
        verify(reportService, times(1)).generateReport();
    }

    @Test
    public void testGetReportById() throws Exception {
        // Подготовка данных для теста
        Long reportId = 1L;
        String reportContent = "Это контент для отчета id 1";

        // Мокируем поведение вашего сервиса с помощью thenReturn()
        when(reportService.getReportContentById(reportId)).thenReturn(reportContent);

        // Выполняем GET-запрос с заданным reportId
        mockMvc.perform(get("/report/api/public/" + reportId))
                .andExpect(status().isOk())
                .andExpect(content().string(reportContent)); // Проверяем, что возвращаемое значение равно ожидаемому reportContent

        // Проверяем, что ваш сервис был вызван с нужными параметрами
        verify(reportService, times(1)).getReportContentById(reportId);
    }
}
