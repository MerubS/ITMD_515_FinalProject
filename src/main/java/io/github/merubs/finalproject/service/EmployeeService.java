package io.github.merubs.finalproject.service;

import io.github.merubs.finalproject.entity.Employee;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EmployeeService {

    @PersistenceContext(unitName = "hrPU")
    private EntityManager em;

    // ── Read ──────────────────────────────────────────────────────────────────

    /**
     * Return all employees ordered by full name.
     * JPQL — no SQL string concatenation.
     */
    public List<Employee> findAll() {
        return em.createQuery(
                        "SELECT e FROM Employee e ORDER BY e.fullName ASC",
                        Employee.class)
                .getResultList();
    }

    /**
     * Return employees filtered by department ID.
     * JPQL with named parameter — no string concatenation.
     */
    public List<Employee> findByDepartment(Long deptId) {
        return em.createQuery(
                        "SELECT e FROM Employee e " +
                                "WHERE e.department.deptId = :deptId " +
                                "ORDER BY e.fullName ASC",
                        Employee.class)
                .setParameter("deptId", deptId)
                .getResultList();
    }

    /**
     * Search employees by name OR title (case-insensitive).
     * JPQL with named parameter — no string concatenation.
     */
    public List<Employee> search(String keyword) {
        String pattern = "%" + keyword.trim().toLowerCase() + "%";
        return em.createQuery(
                        "SELECT e FROM Employee e " +
                                "WHERE LOWER(e.fullName) LIKE :kw " +
                                "   OR LOWER(e.title)    LIKE :kw " +
                                "ORDER BY e.fullName ASC",
                        Employee.class)
                .setParameter("kw", pattern)
                .getResultList();
    }

    /**
     * Find a single employee by primary key.
     */
    public Employee findById(Long id) {
        return em.find(Employee.class, id);
    }

    // ── Write (ADMIN only — enforced via web.xml security constraint) ─────────

    /**
     * Merge (update) an existing employee record.
     * Transaction boundary owned by this EJB method.
     */
    public void update(Employee employee) {
        em.merge(employee);
    }
}

