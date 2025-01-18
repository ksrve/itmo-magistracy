package ksrve.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the EmployeeRepository interface for managing Employee entities.
 * This class provides methods to create, read, update, and delete employee records
 * within a database, utilizing JPA and Hibernate for persistence.
 */
@ApplicationScoped
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Logger logger = LogManager.getLogger(EmployeeRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    /**
     * Constructs an EmployeeRepositoryImpl with the provided SessionFactory.
     *
     * @param sessionFactory The SessionFactory used for creating EntityManager instances.
     */
    @Inject
    public EmployeeRepositoryImpl(final SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * Reads a list of employees that match the given filters.
     *
     * @param filters A list of {@link FilterDTO} to filter employees by specific criteria.
     * @return A list of {@link Employee} entities that match the specified filters.
     */
    @Override
    public List<Employee> read(final List<FilterDTO> filters) {
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filters != null) {
                for (FilterDTO filter : filters) {
                    Predicate predicate = createPredicate(criteriaBuilder, root, filter);
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
            }

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            logger.error("Error executing query", e);
            throw e;
        }
    }

    /**
     * Reads an employee by their unique ID.
     *
     * @param id The unique identifier of the employee to be retrieved.
     * @return An {@link Employee} entity associated with the given ID.
     */
    @Override
    public Employee readById(final Integer id) {
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Employee with id {} not found,", id);
            return null;
        } catch (Exception e) {
            logger.error("Error executing query", e);
            throw e;
        }
    }

    /**
     * Creates a new employee entity in the database.
     *
     * @param employee The {@link Employee} entity to be created.
     * @return The ID of the newly created employee.
     */
    @Override
    public Integer create(Employee employee) {
        int result = -1;

        EntityManager entityManager = null;
        try {
            entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();
            result = employee.getId();
        } catch (ConstraintViolationException e) {
            System.out.println(e.getConstraintName());
            switch (e.getConstraintName()) {
                case "employee_email_key":
                    result = -2;
                    break;
                default: break;
            }
        } catch (Exception e) {
            logger.error("An error occurred while inserting employees. Doing rollback", e);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return result;
    }

    /**
     * Deletes an employee entity from the database by their unique ID.
     *
     * @param id The unique identifier of the employee to be deleted.
     * @return An integer indicating the result of the deletion operation (0 for success).
     */
    @Override
    public Integer delete(final Integer id) {
        int result = -1;

        EntityManager entityManager = null;
        try {
            entityManager = sessionFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Employee employee = readById(id);

            if (employee != null) {
                entityManager.remove(entityManager.contains(employee) ? employee : entityManager.merge(employee));
                result = 0;
            } else {
                logger.warn("Employee with id {} not found, skipping delete", id);
                result = -2;
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error occurred during deletion of employee: {}", e.getMessage());
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }

        return result;
    }

    /**
     * Updates an existing employee entity in the database.
     *
     * @param employee The {@link Employee} entity with updated information.
     * @return The updated {@link Employee} entity.
     */
    @Override
    public Integer update(final Employee employee) {
        int result = -1;
        EntityTransaction transaction = null;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Persist changes
            entityManager.merge(employee);

            // Commit the transaction
            transaction.commit();
            result = employee.getId();
            logger.info("Employee with ID {} updated successfully.", employee.getId());
        } catch (ConstraintViolationException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Constraint violation occurred while updating employee: {}", e.getMessage());
        } catch (NoResultException e) {
            logger.warn("No employee found with ID {}: {}", employee.getId(), e.getMessage());
        } catch (PersistenceException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Persistence error occurred while updating employee: {}", e.getMessage());
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("An unexpected error occurred while updating employee: {}", e.getMessage());
        }
        return result;
    }

    /**
     * Creates a Predicate based on the filter criteria for dynamic queries.
     *
     * @param criteriaBuilder The CriteriaBuilder instance used to create criteria queries.
     * @param root The root of the query, representing the Employee entity.
     * @param filter The filter criteria as a {@link FilterDTO} object.
     * @return A Predicate that represents the filter condition, or null if the operation is unknown.
     */
    private Predicate createPredicate(CriteriaBuilder criteriaBuilder, Root<Employee> root, FilterDTO filter) {
        String key = filter.getKey();
        String operation = filter.getOperation();
        String value = filter.getValue();

        switch (operation) {
            case "==":
                return criteriaBuilder.equal(root.get(key), value);
            case "!=":
                return criteriaBuilder.notEqual(root.get(key), value);
            case ">":
                return criteriaBuilder.greaterThan(root.get(key), value); // Можно добавить обработку типов
            case "<":
                return criteriaBuilder.lessThan(root.get(key), value);
            case ">=":
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), value);
            case "<=":
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), value);
            case "LIKE":
                return criteriaBuilder.like(root.get(key), "%" + value + "%");
            default:
                logger.warn("Unknown operation: {}", operation);
                return null;
        }
    }
}