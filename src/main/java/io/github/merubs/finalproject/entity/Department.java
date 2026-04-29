package io.github.merubs.finalproject.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "dept_name", nullable = false, unique = true, length = 100)
    private String deptName;

    // One department has many employees
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;

    // ── Constructors ──────────────────────────────────────────────────────────
    public Department() {}

    public Department(String deptName) {
        this.deptName = deptName;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getDeptId()                        { return deptId; }
    public void setDeptId(Long deptId)             { this.deptId = deptId; }

    public String getDeptName()                    { return deptName; }
    public void setDeptName(String deptName)       { this.deptName = deptName; }

    public List<Employee> getEmployees()           { return employees; }
    public void setEmployees(List<Employee> emps)  { this.employees = emps; }

    @Override
    public String toString() {
        return deptName;
    }
}
