package com.example.crudapp.repository;

import com.example.crudapp.model.Employee;
import com.example.crudapp.model.Employee.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Page<Employee> findByStatus(EmployeeStatus status, Pageable pageable);

    Page<Employee> findByDepartmentContainingIgnoreCase(String department, Pageable pageable);

    @Query("""
            SELECT e FROM Employee e
            WHERE (:search IS NULL OR :search = ''
                OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.lastName)  LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.email)     LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.department) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.position)  LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:status IS NULL OR e.status = :status)
            AND (:department IS NULL OR :department = '' OR LOWER(e.department) = LOWER(:department))
            """)
    Page<Employee> findWithFilters(
            @Param("search") String search,
            @Param("status") EmployeeStatus status,
            @Param("department") String department,
            Pageable pageable);

    @Query("SELECT DISTINCT e.department FROM Employee e ORDER BY e.department")
    List<String> findAllDepartments();

    long countByStatus(EmployeeStatus status);

    @Query("SELECT COUNT(DISTINCT e.department) FROM Employee e")
    long countDistinctDepartments();
}
