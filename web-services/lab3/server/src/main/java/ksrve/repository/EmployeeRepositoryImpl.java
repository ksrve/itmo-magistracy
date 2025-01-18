package ksrve.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import ksrve.exceptions.EntityAlreadyExistsException;
import ksrve.exceptions.EntityNotFoundException;
import ksrve.exceptions.FieldRequiredException;
import ksrve.exceptions.ValidationException;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

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
     * @throws EntityNotFoundException If no employees match the provided filters.
     */
    @Override
    public List<Employee> read(final List<FilterDTO> filters) throws EntityNotFoundException {
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

            try {
                return entityManager.createQuery(criteriaQuery).getResultList();
            }catch (NoResultException e) {
                logger.error("No employees found");
                throw new EntityNotFoundException("No employees found", -1);
            }
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
     * @throws EntityNotFoundException If no employee exists with the specified ID.
     */
    @Override
    public Employee readById(final Integer id) throws EntityNotFoundException {
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            try {
                return entityManager.createQuery(criteriaQuery).getSingleResult();
            } catch (NoResultException e) {
                logger.error("Employee with id {} not found", id);
                throw new EntityNotFoundException(id);
            }
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
     * @throws ValidationException If there are validation constraints that the employee entity does not satisfy.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @throws EntityAlreadyExistsException If an employee with the same unique identifier already exists.
     */
    @Override
    public Integer create(Employee employee) throws ValidationException, FieldRequiredException, EntityAlreadyExistsException {
        int result = -1;
        EntityTransaction transaction = null;
        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(employee);

            entityManager.getTransaction().commit();
            result = employee.getId();
        } catch (ConstraintViolationException e) {
            throw new ValidationException(e, employee.getId());
        } catch (Exception e) {
            logger.error("An error occurred while inserting employees. Doing rollback", e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }

        return result;
    }

    /**
     * Deletes an employee entity from the database by their unique ID.
     *
     * @param id The unique identifier of the employee to be deleted.
     * @return An integer indicating the result of the deletion operation (0 for success).
     * @throws EntityNotFoundException If no employee exists with the specified ID.
     */
    @Override
    public Integer delete(final Integer id) throws EntityNotFoundException {
        int result = -1;
        EntityTransaction transaction = null;
        try (var entityManager = sessionFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Employee employee = this.readById(id);
            entityManager.remove(entityManager.contains(employee) ? employee : entityManager.merge(employee));

            entityManager.getTransaction().commit();
            result = 0;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }

        return result;
    }

    /**
     * Updates an existing employee entity in the database.
     *
     * @param employee The {@link Employee} entity with updated information.
     * @return The updated {@link Employee} entity.
     * @throws EntityNotFoundException If the employee with the specified ID does not exist.
     * @throws ValidationException If there are validation constraints that the employee entity does not satisfy.
     * @throws FieldRequiredException If any required fields are missing in the employee data.
     * @throws EntityAlreadyExistsException If an employee with the same unique identifier already exists.
     */
    @Override
    public Employee update(final Employee employee)
            throws EntityNotFoundException,ValidationException,
                    FieldRequiredException, EntityAlreadyExistsException {
        EntityTransaction transaction = null;

        try (EntityManager entityManager = sessionFactory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            // Persist changes
            entityManager.merge(employee);

            // Commit the transaction
            transaction.commit();
            logger.info("Employee with ID {} updated successfully.", employee.getId());
        } catch (ConstraintViolationException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new ValidationException(e, employee.getId());

        } catch (NoResultException e) {
            throw new EntityNotFoundException(employee.getId());
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
        return employee;
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