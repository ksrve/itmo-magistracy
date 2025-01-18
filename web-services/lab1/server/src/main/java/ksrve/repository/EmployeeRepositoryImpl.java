package ksrve.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ksrve.model.dto.FilterDTO;
import ksrve.model.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

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

    // @PersistenceContext
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
    public List<Employee> find(final List<FilterDTO> filters) {
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

        // Здесь мы предполагаем, что value может быть parsed в нужный тип
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