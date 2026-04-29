package io.github.merubs.finalproject.web;

import io.github.merubs.finalproject.entity.Department;
import io.github.merubs.finalproject.entity.Employee;
import io.github.merubs.finalproject.service.DepartmentService;
import io.github.merubs.finalproject.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class EmployeeBean {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private DepartmentService departmentService;

    private List<Employee> employees;
    private List<Department> departments;

    // Filter / search inputs from the page
    private Long selectedDeptId;
    private String searchKeyword;

    @PostConstruct
    public void init() {
        departments = departmentService.findAll();

        // Read deptId from URL if coming from departments page
        String param = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("deptId");

        if (param != null && !param.isBlank()) {
            selectedDeptId = Long.parseLong(param);
            employees = employeeService.findByDepartment(selectedDeptId);
        } else {
            employees = employeeService.findAll();
        }
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    /**
     * Filter employees by the selected department dropdown.
     */
    public void filterByDepartment() {
        if (selectedDeptId == null) {
            employees = employeeService.findAll();
        } else {
            employees = employeeService.findByDepartment(selectedDeptId);
        }
    }

    /**
     * Search employees by name or title keyword.
     */
    public void search() {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            employees = employeeService.findAll();
        } else {
            employees = employeeService.search(searchKeyword);
        }
    }

    /**
     * Clear all filters and reload the full list.
     */
    public void clearFilters() {
        selectedDeptId = null;
        searchKeyword  = null;
        employees      = employeeService.findAll();
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public List<Employee> getEmployees()               { return employees; }
    public List<Department> getDepartments()           { return departments; }

    public Long getSelectedDeptId()                    { return selectedDeptId; }
    public void setSelectedDeptId(Long id)             { this.selectedDeptId = id; }

    public String getSearchKeyword()                   { return searchKeyword; }
    public void setSearchKeyword(String kw)            { this.searchKeyword = kw; }
}

