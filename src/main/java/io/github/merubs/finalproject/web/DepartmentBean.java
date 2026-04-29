package io.github.merubs.finalproject.web;

import io.github.merubs.finalproject.entity.Department;
import io.github.merubs.finalproject.service.DepartmentService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class DepartmentBean {

    @Inject
    private DepartmentService departmentService;

    private List<Department> departments;

    @PostConstruct
    public void init() {
        departments = departmentService.findAll();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public List<Department> getDepartments() {
        return departments;
    }
}
