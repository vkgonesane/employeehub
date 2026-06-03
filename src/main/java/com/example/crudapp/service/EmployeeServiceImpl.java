package com.example.crudapp.service;

import com.example.crudapp.dto.EmployeeDto;
import com.example.crudapp.exception.DuplicateEmailException;
import com.example.crudapp.exception.ResourceNotFoundException;
import com.example.crudapp.model.Employee;
import com.example.crudapp.model.Employee.EmployeeStatus;
import com.example.crudapp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> findAll(String search, EmployeeStatus status, String department, Pageable pageable) {
        log.debug("Fetching employees - search={}, status={}, dept={}", search, status, department);
        return employeeRepository
                .findWithFilters(search, status, department, pageable)
                .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto findById(Long id) {
        return employeeRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    @Override
    public EmployeeDto create(EmployeeDto dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException(dto.getEmail());
        }
        Employee employee = toEntity(dto);
        Employee saved = employeeRepository.save(employee);
        log.info("Created employee with id={}", saved.getId());
        return toDto(saved);
    }

    @Override
    public EmployeeDto update(Long id, EmployeeDto dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        if (employeeRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateEmailException(dto.getEmail());
        }

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDepartment(dto.getDepartment());
        existing.setPosition(dto.getPosition());
        existing.setSalary(dto.getSalary());
        existing.setHireDate(dto.getHireDate());
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        Employee updated = employeeRepository.save(existing);
        log.info("Updated employee with id={}", updated.getId());
        return toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeeRepository.deleteById(id);
        log.info("Deleted employee with id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllDepartments() {
        return employeeRepository.findAllDepartments();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getStatistics() {
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("total", employeeRepository.count());
        stats.put("active", employeeRepository.countByStatus(EmployeeStatus.ACTIVE));
        stats.put("inactive", employeeRepository.countByStatus(EmployeeStatus.INACTIVE));
        stats.put("onLeave", employeeRepository.countByStatus(EmployeeStatus.ON_LEAVE));
        stats.put("departments", employeeRepository.countDistinctDepartments());
        return stats;
    }

    // ─── Mappers ────────────────────────────────────────────────────────────────

    private EmployeeDto toDto(Employee e) {
        return EmployeeDto.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .email(e.getEmail())
                .department(e.getDepartment())
                .position(e.getPosition())
                .salary(e.getSalary())
                .hireDate(e.getHireDate())
                .status(e.getStatus())
                .build();
    }

    private Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .salary(dto.getSalary())
                .hireDate(dto.getHireDate())
                .status(dto.getStatus() != null ? dto.getStatus() : EmployeeStatus.ACTIVE)
                .build();
    }
}
