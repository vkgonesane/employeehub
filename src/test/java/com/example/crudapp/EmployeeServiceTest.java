package com.example.crudapp;

import com.example.crudapp.dto.EmployeeDto;
import com.example.crudapp.exception.DuplicateEmailException;
import com.example.crudapp.exception.ResourceNotFoundException;
import com.example.crudapp.model.Employee;
import com.example.crudapp.model.Employee.EmployeeStatus;
import com.example.crudapp.repository.EmployeeRepository;
import com.example.crudapp.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee sampleEmployee;
    private EmployeeDto sampleDto;

    @BeforeEach
    void setUp() {
        sampleEmployee = Employee.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .department("Engineering")
                .position("Developer")
                .salary(new BigDecimal("75000.00"))
                .hireDate(LocalDate.of(2022, 1, 15))
                .status(EmployeeStatus.ACTIVE)
                .build();

        sampleDto = EmployeeDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .department("Engineering")
                .position("Developer")
                .salary(new BigDecimal("75000.00"))
                .hireDate(LocalDate.of(2022, 1, 15))
                .status(EmployeeStatus.ACTIVE)
                .build();
    }

    @Test
    void findById_existingId_returnsDto() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));

        EmployeeDto result = employeeService.findById(1L);

        assertThat(result.getFirstName()).isEqualTo("Jane");
        assertThat(result.getEmail()).isEqualTo("jane.doe@example.com");
    }

    @Test
    void findById_nonExistingId_throwsNotFoundException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_newEmail_savesEmployee() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(sampleEmployee);

        EmployeeDto result = employeeService.create(sampleDto);

        assertThat(result.getFullName()).isEqualTo("Jane Doe");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void create_duplicateEmail_throwsDuplicateEmailException() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> employeeService.create(sampleDto))
                .isInstanceOf(DuplicateEmailException.class);

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void delete_existingId_callsDeleteById() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.delete(1L);

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void delete_nonExistingId_throwsNotFoundException() {
        when(employeeRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
