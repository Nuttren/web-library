package ru.skypro.lessons.springboot.weblibrary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.lessons.springboot.weblibrary.config.TestSecurityConfig;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class) // Импорт тестовой конфигурации
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
public class EmployeeControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private EmployeeRepository employeeRepository;

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
    @DirtiesContext
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

        // Выполнение POST-запроса и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Проверка, что данные были сохранены в базу данных H2
        List<Employee> savedEmployees = (List<Employee>) employeeRepository.findAll();
        assertThat(savedEmployees).hasSize(1);
        assertThat(savedEmployees.get(0).getName()).isEqualTo("John");
        assertThat(savedEmployees.get(0).getSalary()).isEqualTo(5000);
    }


    // Вспомогательный метод для преобразования объекта в JSON строку
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        // Подготовка данных в тестовой базе данных
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Jane");
        employee.setSalary(3000);
        employeeRepository.save(employee);

        // Создание объекта для обновления
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John");
        employeeDTO.setSalary(5000);

        // Выполнение PUT-запроса и проверка результата
        mockMvc.perform(put("/admin/update/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDTO)))
                .andExpect(status().isOk());

        // Проверка, что данные были обновлены в базе данных H2
        Optional<Employee> updatedEmployee = employeeRepository.findById(employee.getId());
        assertThat(updatedEmployee).isPresent();
        assertThat(updatedEmployee.get().getName()).isEqualTo("John");
        assertThat(updatedEmployee.get().getSalary()).isEqualTo(5000);

    }

    @Test
    public void deleteUpdateEmployee() throws Exception {
        // Подготовка данных в тестовой базе данных
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSalary(5000);
        employeeRepository.save(employee);

        // Выполнение DELETE-запроса и проверка результата
        mockMvc.perform(delete("/admin/delete/{id}", employee.getId()))
                .andExpect(status().isOk());

        // Проверка, что сотрудник был удален из базы данных H2
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isEmpty();

    }

    @Test
    public void testFindEmployeeWithMaxSalary() throws Exception {
        // Подготовка данных в тестовой базе данных
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John");
        employee1.setSalary(5000);
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane");
        employee2.setSalary(10000);
        employeeRepository.save(employee2);

        // Выполнение GET-запроса и проверка результата
        mockMvc.perform(get("/admin/salary/max").with(request -> {
                    request.setRemoteUser("admin");
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.salary").value(10000));
    }

    @Test
    public void testGetEmployeeFullInfo() throws Exception {
        // Подготовка данных в тестовой базе данных
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSalary(5000);
        employee.setPositionId(1L);
        Position position = new Position();
        position.setPositionId(1L);
        position.setPositionName("Developer");
        employee.setPosition(position);
        employeeRepository.save(employee);

        // Выполнение GET-запроса и проверка результата
        mockMvc.perform(get("/user/employees/{id}/fullInfo", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.salary").value(5000.0))
                .andExpect(jsonPath("$.position").value("Developer"));
    }

    @Test
    public void testGetEmployeesByPage() throws Exception {
        // Подготовка данных в тестовой базе данных
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John");
        employee1.setSalary(5000);
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane");
        employee2.setSalary(10000);
        employeeRepository.save(employee2);

        // Выполнение GET-запроса и проверка результата
        mockMvc.perform(get("/user/employees/page").param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].salary").value(5000))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                .andExpect(jsonPath("$[1].salary").value(10000));
    }

    @Test

    public void testUploadEmployees() throws Exception {
        // Создание списка сотрудников для загрузки
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setName("John");
        employee1.setSalary(5000);
        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setName("Jane");
        employee2.setSalary(10000);
        employeeDTOList.add(employee1);
        employeeDTOList.add(employee2);

        // Преобразование списка сотрудников в JSON-строку
        String fileContent = new ObjectMapper().writeValueAsString(employeeDTOList);

        // Создание MultipartFile из JSON-строки
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "employees.json",
                MediaType.APPLICATION_JSON_VALUE,
                fileContent.getBytes(StandardCharsets.UTF_8)
        );

        // Выполнение POST-запроса на загрузку сотрудников и проверка статуса ответа
        mockMvc.perform(MockMvcRequestBuilders.multipart("/public/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Employees uploaded successfully"));
    }

}


