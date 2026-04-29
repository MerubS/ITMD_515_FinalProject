# HR Directory

A small enterprise-style HR web application built with Jakarta EE (JSF/Facelets, JPA, EJB) and deployed on Payara Server 6.

## What the App Does

- **Department List** — view all departments and navigate to employees by department
- **Employee List** — view all employees with department filter and keyword search
- **Edit Employee** — ADMIN-only page to update an employee's title, salary, and department
- **RBAC Security** — USER role can view only; ADMIN role can edit

## Tech Stack

| Layer | Technology |
|-------|-----------|
| View | JSF 4 / Facelets (.xhtml) |
| Backing Beans | CDI `@Named` + `@RequestScoped` / `@ViewScoped` |
| Service Layer | EJB `@Stateless` |
| Persistence | JPA / EclipseLink + JPQL |
| Database | MySQL 8 via `jdbc/HrDS` |
| Server | Payara Server 6 |

## URLs to Key Pages

| Page | URL |
|------|-----|
| Department List | `http://localhost:8080/FinalProject/departments.xhtml` |
| Employee List | `http://localhost:8080/FinalProject/employees.xhtml` |
| Edit Employee | `http://localhost:8080/FinalProject/editEmployee.xhtml?empId=1` |

## Project Structure

```
src/main/
  java/io/github/merubs/finalproject/
    entity/       Department.java, Employee.java
    service/      DepartmentService.java, EmployeeService.java
    web/          DepartmentBean.java, EmployeeBean.java, EmployeeEditBean.java
  resources/META-INF/
    persistence.xml
    import.sql
  webapp/
    departments.xhtml
    employees.xhtml
    editEmployee.xhtml
    WEB-INF/
      web.xml
      beans.xml
      templates/main.xhtml
```
