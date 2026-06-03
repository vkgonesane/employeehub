package com.example.crudapp.service;

import com.example.crudapp.dto.EmployeeDto;
import com.example.crudapp.model.Employee.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    Page<EmployeeDto> findAll(String search, EmployeeStatus status, String department, Pageable pageable);

    EmployeeDto findById(Long id);

    EmployeeDto create(EmployeeDto dto);

    EmployeeDto update(Long id, EmployeeDto dto);

    void delete(Long id);

    List<String> findAllDepartments();

    Map<String, Long> getStatistics();
}
