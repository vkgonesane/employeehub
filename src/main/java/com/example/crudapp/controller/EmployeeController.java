package com.example.crudapp.controller;

import com.example.crudapp.dto.EmployeeDto;
import com.example.crudapp.exception.DuplicateEmailException;
import com.example.crudapp.model.Employee.EmployeeStatus;
import com.example.crudapp.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) EmployeeStatus status,
            @RequestParam(defaultValue = "") String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EmployeeDto> employees = employeeService.findAll(search, status, department, pageable);

        model.addAttribute("employees", employees);
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("stats", employeeService.getStatistics());
        model.addAttribute("statuses", EmployeeStatus.values());
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("department", department);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("statuses", EmployeeStatus.values());
        model.addAttribute("pageTitle", "Add New Employee");
        model.addAttribute("formAction", "/employees");
        return "employees/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("employee") EmployeeDto dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("departments", employeeService.findAllDepartments());
            model.addAttribute("statuses", EmployeeStatus.values());
            model.addAttribute("pageTitle", "Add New Employee");
            model.addAttribute("formAction", "/employees");
            return "employees/form";
        }

        try {
            EmployeeDto created = employeeService.create(dto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employee '" + created.getFullName() + "' created successfully!");
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            model.addAttribute("departments", employeeService.findAllDepartments());
            model.addAttribute("statuses", EmployeeStatus.values());
            model.addAttribute("pageTitle", "Add New Employee");
            model.addAttribute("formAction", "/employees");
            return "employees/form";
        }

        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("statuses", EmployeeStatus.values());
        model.addAttribute("pageTitle", "Edit Employee");
        model.addAttribute("formAction", "/employees/" + id);
        return "employees/form";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("employee") EmployeeDto dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("departments", employeeService.findAllDepartments());
            model.addAttribute("statuses", EmployeeStatus.values());
            model.addAttribute("pageTitle", "Edit Employee");
            model.addAttribute("formAction", "/employees/" + id);
            return "employees/form";
        }

        try {
            EmployeeDto updated = employeeService.update(id, dto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employee '" + updated.getFullName() + "' updated successfully!");
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "duplicate", e.getMessage());
            model.addAttribute("departments", employeeService.findAllDepartments());
            model.addAttribute("statuses", EmployeeStatus.values());
            model.addAttribute("pageTitle", "Edit Employee");
            model.addAttribute("formAction", "/employees/" + id);
            return "employees/form";
        }

        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully.");
        return "redirect:/employees";
    }
}
