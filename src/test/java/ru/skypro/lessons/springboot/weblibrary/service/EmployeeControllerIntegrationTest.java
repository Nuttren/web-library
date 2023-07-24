package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.weblibrary.config.TestSecurityConfig;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class) // Импорт тестовой конфигурации
@AutoConfigureTestDatabase
@Transactional
public class EmployeeControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    public void testGetAllEmployees() throws Exception {
        // Создание тестовых данных
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John");
        employee1.setSalary(5000);
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Alice");
        employee2.setSalary(7000);
        Employee employee3 = new Employee();
        employee3.setId(3L);
        employee3.setName("Bob");
        employee3.setSalary(6000);


        // Сохраняем тестовые данные в базу данных H2
        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
        logger.info("After saving employees: {}", employeeRepository.findAll());

        // Выполняем GET запрос на адрес "/public/list"
        mockMvc.perform(get("/public/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))) // Проверка наличия 3 элементов в списке
                .andExpect(jsonPath("$[0].name").value("John")) // Проверка имени первого сотрудника
                .andExpect(jsonPath("$[0].salary").value(5000)) // Проверка зарплаты первого сотрудника
                .andExpect(jsonPath("$[1].name").value("Alice")) // Проверка имени второго сотрудника
                .andExpect(jsonPath("$[1].salary").value(7000)) // Проверка зарплаты второго сотрудника
                .andExpect(jsonPath("$[2].name").value("Bob")) // Проверка имени третьего сотрудника
                .andExpect(jsonPath("$[2].salary").value(6000)); // Проверка зарплаты третьего сотрудника
    }

    @Test
    public void testCreateEmployee() throws Exception {
        // Создание тестового EmployeeDTO
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John");
        employeeDTO.setSalary(5000);

        // Настройка мок-сервиса для успешного создания сотрудника
        doNothing().when(employeeService).createEmployee(any(EmployeeDTO.class));

        // Выполнение POST-запроса и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Проверка, что метод createEmployee был вызван с правильным аргументом
        verify(employeeService, times(1)).createEmployee(eq(employeeDTO));
    }


    // Вспомогательный метод для преобразования объекта в JSON строку
    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        long employeeId = 1L;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John");
        employeeDTO.setSalary(5000);
        mockMvc.perform(put("/admin/update/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDTO)))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).updateEmployee(employeeId, employeeDTO);
    }

    @Test
    public void deleteUpdateEmployee() throws Exception {
        long employeeId = 1L;

        mockMvc.perform(delete("/admin/delete/{id}", employeeId))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).removeEmployee(employeeId);
    }

    @Test
    public void testFindEmployeeWithMaxSalary() throws Exception {
        // Создание тестового EmployeeDTO с максимальной зарплатой
        EmployeeDTO employeeWithMaxSalary = new EmployeeDTO();
        employeeWithMaxSalary.setName("John");
        employeeWithMaxSalary.setSalary(10000);

        // Настройка мок-сервиса для возвращения сотрудника с максимальной зарплатой
        when(employeeService.findEmployeeWithMaxSalary()).thenReturn(employeeWithMaxSalary);

        // Выполнение GET-запроса и проверка результата
        mockMvc.perform(get("/admin/salary/max").with(request -> {
                    request.setRemoteUser("admin");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.salary").value(10000));

        // Проверка, что метод findEmployeeWithMaxSalary был вызван
        verify(employeeService, times(1)).findEmployeeWithMaxSalary();
    }

    @Test
    public void testGetEmployeeFullInfo() throws Exception {
        long employeeId = 1L;
        EmployeeFullInfo employeeFullInfo = new EmployeeFullInfo("John", 5000.0, "Developer");

        // Настройка мок-сервиса
        when(employeeService.getEmployeeFullInfo(employeeId)).thenReturn(employeeFullInfo);

        // Выполнение GET-запроса и проверка результата
        mockMvc.perform(get("/user/employees/{id}/fullInfo", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.salary").value(5000.0))
                .andExpect(jsonPath("$.position").value("Developer"));

        // Проверка, что метод mock-сервиса был вызван один раз с аргументом employeeId
        verify(employeeService, times(1)).getEmployeeFullInfo(employeeId);
    }

    @Test
    public void testGetEmployeesByPage() throws Exception {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setName("John");
        employee1.setSalary(5000);
        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setName("Alice");
        employee2.setSalary(7000);
        employeeDTOList.add(employee1);
        employeeDTOList.add(employee2);

        when(employeeService.getEmployeesByPage(0)).thenReturn(employeeDTOList);
        mockMvc.perform(get("/user/employees/page").param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].salary").value(5000))
                .andExpect(jsonPath("$[1].name").value("Alice"))
                .andExpect(jsonPath("$[1].salary").value(7000));

        // Проверка, что метод mock-сервиса был вызван один раз с аргументом page=0
        verify(employeeService, times(1)).getEmployeesByPage(0);
    }

    @Test
    public void testUploadEmployees() throws Exception {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setName("John");
        employee1.setSalary(5000);
        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setName("Alice");
        employee2.setSalary(7000);
        employeeDTOList.add(employee1);
        employeeDTOList.add(employee2);
        String fileContent = new ObjectMapper().writeValueAsString(employeeDTOList);

        // Создание MultipartFile из данных тестового файла
        MockMultipartFile file = new MockMultipartFile("file", "employees.json", MediaType.APPLICATION_JSON_VALUE, fileContent.getBytes());

        // Выполнение POST-запроса и проверка статуса ответа
        mockMvc.perform(multipart("/public/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Employees uploaded successfully"));

        // Проверка, что метод mock-сервиса был вызван один раз с аргументом employees
        verify(employeeService, times(1)).saveAllEmployees(employeeDTOList);
    }
}


