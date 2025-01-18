package ksrve.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import ksrve.model.entity.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for managing Hibernate SessionFactory.
 * <p>
 * The {@code HibernateUtil} class provides a method to create and configure
 * a Hibernate {@link SessionFactory}. It reads database connection settings
 * from a properties file, allowing for configurable database connections.
 * </p>
 * <p>
 * This class is marked as an {@link ApplicationScoped} bean, which means it
 * will be instantiated once per application and shared across all components
 * that require a Hibernate SessionFactory.
 * </p>
 */
@ApplicationScoped
public class HibernateUtil {

    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    /**
     * Builds the Hibernate SessionFactory.
     * <p>
     * This method reads configuration properties from a file named "configuration.properties",
     * sets Hibernate configurations, and builds a {@link SessionFactory} instance.
     * It is annotated with {@link Produces} and {@link Singleton}, indicating that it
     * should be used to produce a single instance of the SessionFactory across
     * the application.
     * </p>
     *
     * @return a configured {@link SessionFactory} instance used for creating
     *         Hibernate sessions.
     * @throws ExceptionInInitializerError if the SessionFactory creation fails.
     */
    @Produces
    @Singleton
    public static SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("configuration.properties")) {
                if (input == null) {
                    logger.error("Sorry, unable to find configuration.properties");
                    throw new FileNotFoundException("configuration.properties not found in the classpath");
                }
                properties.load(input);
            }

            String hostname = System.getProperty("db.host", properties.getProperty("db.host"));
            String port = System.getProperty("db.port", properties.getProperty("db.port"));
            String name = System.getProperty("db.name", properties.getProperty("db.name"));
            String connectionUrl = String.format("jdbc:postgresql://%s:%s/%s", hostname, port, name);

            String user = System.getProperty("db.user", properties.getProperty("db.username"));
            String password = System.getProperty("db.password", properties.getProperty("db.password"));

            // Creating Hibernate configuration and setting properties
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Employee.class);
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.show_sql", true);
            configuration.setProperty("hibernate.connection.url", connectionUrl);
            configuration.setProperty("hibernate.connection.username", user);
            configuration.setProperty("hibernate.connection.password", password);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed: ", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}