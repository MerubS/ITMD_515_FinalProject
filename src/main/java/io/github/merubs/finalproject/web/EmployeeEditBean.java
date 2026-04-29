package io.github.merubs.finalproject.web;

import io.github.merubs.finalproject.entity.Department;
import io.github.merubs.finalproject.entity.Employee;
import io.github.merubs.finalproject.service.DepartmentService;
import io.github.merubs.finalproject.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ViewScoped
public class EmployeeEditBean implements Serializable {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private DepartmentService departmentService;

    private Employee employee;
    private List<Department> departments;

    // empId passed as query parameter: editEmployee.xhtml?empId=3
    private Long empId;

    @PostConstruct
    public void init() {
        departments = departmentService.findAll();

        // Load employee from query param
        FacesContext ctx = FacesContext.getCurrentInstance();
        String param = ctx.getExternalContext()
                .getRequestParameterMap()
                .get("empId");
        if (param != null) {
            try {
                empId    = Long.parseLong(param);
                employee = employeeService.findById(empId);
            } catch (NumberFormatException e) {
                employee = null;
            }
        }
    }

    // ── Action ────────────────────────────────────────────────────────────────

    /**
     * Save updates — title and salary are validated by JSF before this runs.
     * ADMIN access enforced by web.xml security constraint on /editEmployee.xhtml.
     */
    public String save() {
        if (employee == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Employee not found.", null));
            return null;
        }

        // Re-attach the correct Department entity by ID
        if (employee.getDepartment() != null && employee.getDepartment().getDeptId() != null) {
            Department dept = departmentService.findById(
                    employee.getDepartment().getDeptId());
            employee.setDepartment(dept);
        }

        employeeService.update(employee);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Employee updated successfully!", null));

        // Stay on the same page and show success message
        return null;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Employee getEmployee()                    { return employee; }
    public void setEmployee(Employee employee)       { this.employee = employee; }

    public List<Department> getDepartments()         { return departments; }

    public Long getEmpId()                           { return empId; }
    public void setEmpId(Long empId)                 { this.empId = empId; }
}

