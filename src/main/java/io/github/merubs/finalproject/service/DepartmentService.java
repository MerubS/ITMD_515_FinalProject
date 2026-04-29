package io.github.merubs.finalproject.service;

import io.github.merubs.finalproject.entity.Department;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DepartmentService {

    @PersistenceContext(unitName = "hrPU")
    private EntityManager em;

    /**
     * Returns all departments ordered by name.
     * JPQL — no string concatenation.
     */
    public List<Department> findAll() {
        return em.createQuery(
                        "SELECT d FROM Department d ORDER BY d.deptName ASC",
                        Department.class)
                .getResultList();
    }

    /**
     * Find a single department by its primary key.
     */
    public Department findById(Long id) {
        return em.find(Department.class, id);
    }
}