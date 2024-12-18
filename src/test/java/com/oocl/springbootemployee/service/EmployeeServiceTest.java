package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeCreateInvalidException;
import com.oocl.springbootemployee.exception.EmployeeInactivityException;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.IEmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    IEmployeeRepository mockedEmployeeRepository;

    @Test
    void should_return_the_given_employees_when_getAllEmployees() {
        //given
        when(mockedEmployeeRepository.getAll()).thenReturn(List.of(new Employee(1, "Lucy", 18, Gender.FEMALE, 8000.0)));

        //when
        List<Employee> allEmployees = employeeService.getAllEmployees();

        //then
        assertEquals(1, allEmployees.size());
        assertEquals("Lucy", allEmployees.get(0).getName());
    }

    @Test
    void should_return_the_created_employee_when_create_given_a_employee() {
        //given
        Employee lucy = new Employee(1, "Lucy", 18, Gender.FEMALE, 8000.0);
        when(mockedEmployeeRepository.addEmployee(any())).thenReturn(lucy);

        //when
        Employee createdEmployee = employeeService.creat(lucy);

        //then
        assertEquals("Lucy", createdEmployee.getName());
    }

    @Test
    public void should_throw_EmployeeAgeInvalidException_when_create_given_a_employee_with_age_6() {
        // Given
        Employee kiKi = new Employee(1, "KiKi", 6, Gender.FEMALE, 8000.0);
        // When
        // Then
        assertThrows(EmployeeCreateInvalidException.class, () -> employeeService.creat(kiKi));
        verify(mockedEmployeeRepository, never()).addEmployee(any());
    }
    
    @Test
    public void should_throw_exception_when_create_given_a_employee_with_age_31_salary_1() {
        // Given
        Employee kiKi = new Employee(1, "KiKi", 31, Gender.FEMALE, 1.0);
        // When
        // Then
        assertThrows(EmployeeCreateInvalidException.class, () -> employeeService.creat(kiKi));
        verify(mockedEmployeeRepository, never()).addEmployee(any());
    }

    @Test
    public void should_created_when_create_given_a_employee_with_activity() {
        // Given
        Employee kiKi = new Employee(1, "KiKi", 30, Gender.FEMALE, 100000.0);
        // When
        // Then
        employeeService.creat(kiKi);
        verify(mockedEmployeeRepository).addEmployee(argThat(Employee::getActivity));
    }

    @Test
    public void should_throw_exception_when_update_given_a_employee_no_activity() {
        // Given
        Employee kiKi = new Employee(1, "KiKi", 30, Gender.FEMALE, 1000000.0);
        kiKi.setActivity(false);
        // When
        when(mockedEmployeeRepository.getEmployeeById(1)).thenReturn(kiKi);
        // Then
        assertThrows(EmployeeInactivityException.class, () -> employeeService.update(1, kiKi));
        verify(mockedEmployeeRepository, never()).updateEmployee(any(), any());
    }
}
