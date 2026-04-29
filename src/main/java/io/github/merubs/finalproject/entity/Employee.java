package io.github.merubs.finalproject.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    // ── Constructors ──────────────────────────────────────────────────────────
    public Employee() {}

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getEmpId()                           { return empId; }
    public void setEmpId(Long empId)                 { this.empId = empId; }

    public String getFullName()                      { return fullName; }
    public void setFullName(String fullName)         { this.fullName = fullName; }

    public String getTitle()                         { return title; }
    public void setTitle(String title)               { this.title = title; }

    public Department getDepartment()                { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public BigDecimal getSalary()                    { return salary; }
    public void setSalary(BigDecimal salary)         { this.salary = salary; }

    public LocalDate getHireDate()                   { return hireDate; }
    public void setHireDate(LocalDate hireDate)      { this.hireDate = hireDate; }
}

