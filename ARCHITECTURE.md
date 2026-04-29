# Architecture Documentation

## Layer Overview

```
Browser (HTTP)
    ↓
JSF/Facelets View (.xhtml)
    ↓
CDI Backing Beans (@Named)
    ↓
EJB Service Layer (@Stateless)
    ↓
JPA / EclipseLink (EntityManager)
    ↓
MySQL Database (via jdbc/HrDS)
```

## Key Classes and Responsibilities

- **`departments.xhtml`** — Facelets page that displays all departments in a card grid; each card has a "View Employees" link that passes `deptId` as a query parameter to `employees.xhtml`

- **`employees.xhtml`** — Facelets page that displays the full employee table with a department dropdown filter and keyword search bar; Edit button links to `editEmployee.xhtml?empId=X`

- **`editEmployee.xhtml`** — ADMIN-only Facelets page with a form to update an employee's title, salary, and department; includes JSF validation and a success/error message panel

- **`DepartmentBean`** — `@Named @RequestScoped` backing bean; calls `DepartmentService.findAll()` on `@PostConstruct` and exposes the department list to `departments.xhtml`

- **`EmployeeBean`** — `@Named @RequestScoped` backing bean; loads all employees on init, handles filter by department and keyword search actions by delegating to `EmployeeService`

- **`EmployeeEditBean`** — `@Named @ViewScoped` backing bean; reads `empId` query parameter on `@PostConstruct`, loads the employee, and calls `EmployeeService.update()` on save; `@ViewScoped` keeps state alive across the form POST

- **`DepartmentService`** — `@Stateless` EJB; owns the transaction boundary for department reads; uses JPQL `SELECT d FROM Department d ORDER BY d.deptName ASC`

- **`EmployeeService`** — `@Stateless` EJB; provides `findAll()`, `findByDepartment(deptId)`, `search(keyword)`, `findById(id)`, and `update(employee)`; all JPQL uses named parameters — no string concatenation

- **`Department`** — `@Entity` mapped to the `department` table; has `@OneToMany` relationship to `Employee`

- **`Employee`** — `@Entity` mapped to the `employee` table; has `@ManyToOne(EAGER)` join to `Department`

- **`persistence.xml`** — defines the `hrPU` persistence unit pointing to `jdbc/HrDS` (MySQL); `eclipselink.ddl-generation=none` so tables are not dropped on redeploy

- **`web.xml`** — declares the Faces Servlet, RBAC security constraints (ADMIN-only on `/editEmployee.xhtml`), BASIC auth against the Payara file realm, and role declarations for USER and ADMIN

- **`beans.xml`** — located in `WEB-INF/` with `bean-discovery-mode="all"` to activate CDI scanning for the entire WAR so `@Inject` works in all backing beans