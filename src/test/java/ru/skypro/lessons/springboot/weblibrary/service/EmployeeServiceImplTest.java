package ru.skypro.lessons.springboot.weblibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exeption.IncorrectEmployeeIdException;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.EmployeeFullInfo;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testCreateEmployee() {
        // Создание тестовых данных
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setSalary(5000);
        employeeDTO.setPositionId(1L);

        // Настройка моков
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        // Вызов тестируемого метода
        employeeService.createEmployee(employeeDTO);

        // Проверка вызовов методов
        verify(employeeRepository).save(any(Employee.class));
    }


    @ParameterizedTest
    @CsvSource({"1,John Doe,5000", "2,Jane Smith,6000"})
    public void testUpdateEmployee(long employeeId, String name, int salary) {
        // Создание тестовых данных
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(name);
        employeeDTO.setSalary(salary);

        // Настройка моков
        Employee existingEmployee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        // Вызов тестируемого метода
        employeeService.updateEmployee(employeeId, employeeDTO);

        // Проверка вызовов методов
        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).save(any(Employee.class));
    }
    @Test
    public void testUpdateEmployee_InvalidId() {
        // Создание тестовых данных
        long invalidId = 9999L;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("John Doe");
        employeeDTO.setSalary(5000);

        // Настройка мока
        when(employeeRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Вызов тестируемого метода и проверка выбрасываемого исключения
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(invalidId, employeeDTO);
        });

        // Проверка вызова метода findById
        verify(employeeRepository).findById(invalidId);
    }

    @Test
    public void testGetAllEmployees() {
        // Создание тестовых данных
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");
        employee1.setSalary(5000);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Smith");
        employee2.setSalary(6000);

        List<Employee> employees = Arrays.asList(employee1, employee2);

        // Настройка мока
        when(employeeRepository.findAll()).thenReturn(employees);

        // Вызов тестируемого метода
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Проверка результатов
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals(5000, result.get(0).getSalary());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Jane Smith", result.get(1).getName());
        assertEquals(6000, result.get(1).getSalary());

        // Проверка вызова метода findAll
        verify(employeeRepository).findAll();
    }


    @Test
    public void testGetEmployeeById_ValidId() {
        // Создание тестового сотрудника
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setSalary(5000);

        // Настройка мока
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Вызов тестируемого метода
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(5000, result.getSalary());

        // Проверка вызова метода findById
        verify(employeeRepository).findById(1L);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    public void testGetEmployeeById_InvalidId(long id) {
        // Настройка мока
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Вызов тестируемого метода и проверка выбрасывания исключения
        assertThrows(IncorrectEmployeeIdException.class, () -> employeeService.getEmployeeById(id));

        // Проверка вызова метода findById
        verify(employeeRepository).findById(id);
    }

    @Test
    public void testRemoveEmployee() {
        // Вызов тестируемого метода
        employeeService.removeEmployee(1L);

        // Проверка вызова метода deleteById
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void testGetEmployeeByName() {
        // Создание тестовых данных
        String name = "John Doe";
        Employee employee = new Employee();
        employee.setName(name);

        // Настройка мока репозитория
        when(employeeRepository.findByName(name)).thenReturn(List.of(employee));

        // Вызов тестируемого метода
        Employee result = employeeService.getEmployeeByName(name);

        // Проверка результата
        assertEquals(employee, result);
    }


    @Test
    void testGetEmployeesByPosition() {

        // Создание тестовых данных
        Position position = new Position(1L, "Developer");
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");
        employee1.setSalary(6000);
        employee1.setPositionId(1L);
        employee1.setPosition(position);
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Smith");
        employee2.setSalary(6000);
        employee2.setPositionId(1L);
        employee2.setPosition(position);
        List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

        // Настройка поведения репозитория
        when(employeeRepository.findByPosition(position)).thenReturn(expectedEmployees);

        // Вызов тестируемого метода
        List<EmployeeDTO> actualEmployees = employeeService.getEmployeesByPosition(position);

        // Проверка результатов
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        // Здесь также можно проверить соответствие ожидаемых значений и фактических значений для каждого сотрудника

        // Проверка вызовов репозитория
        verify(employeeRepository, times(1)).findByPosition(position);
    }


    @Test
    void testFindEmployeeWithMaxSalary() {
        // Create test data
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");
        employee1.setSalary(5000);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Smith");
        employee2.setSalary(7000);

        List<Employee> allEmployees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(allEmployees);

        EmployeeDTO actualEmployee = employeeService.findEmployeeWithMaxSalary();

        EmployeeDTO expectedEmployeeDTO = EmployeeDTO.fromEmployee(employee2);

        assertEquals(expectedEmployeeDTO, actualEmployee);

        verify(employeeRepository).findAll();
    }


    @Test
    void testGetEmployeeFullInfo() {
        // Создание тестовых данных
        long employeeId = 1L;
        String employeeName = "John Doe";
        int employeeSalary = 5000;
        String positionName = "Developer";

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName(employeeName);
        employee.setSalary(employeeSalary);

        Position position = new Position();
        position.setPositionName(positionName);

        employee.setPosition(position);

        // Настройка поведения репозитория
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Вызов тестируемого метода
        EmployeeFullInfo result = employeeService.getEmployeeFullInfo(employeeId);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(employeeName, result.getName());
        assertEquals(employeeSalary, result.getSalary());
        assertEquals(positionName, result.getPosition());

        // Проверка вызовов репозитория
        verify(employeeRepository, times(1)).findById(employeeId);
    }


    @Test
    public void testGetEmployeesByPage() {
        // Создание тестовых данных
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");
        employee1.setSalary(6000);
        employee1.setPositionId(1L);
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Smith");
        employee2.setSalary(6000);
        employee2.setPositionId(1L);
        List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

        int page = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize);


        Page<Employee> employeePage = new PageImpl<>(expectedEmployees, pageRequest, expectedEmployees.size());
        when(employeeRepository.findAll(pageRequest)).thenReturn(employeePage);


        List<EmployeeDTO> actualEmployees = employeeService.getEmployeesByPage(page);


        assertEquals(expectedEmployees.size(), actualEmployees.size());


        verify(employeeRepository, times(1)).findAll(pageRequest);
    }

}