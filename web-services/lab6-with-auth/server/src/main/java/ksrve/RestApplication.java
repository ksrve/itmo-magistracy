package ksrve;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ksrve.exceptions.EntityAlreadyExistsExceptionMapper;
import ksrve.exceptions.EntityNotFoundExceptionMapper;
import ksrve.exceptions.FieldRequiredExceptionMapper;
import ksrve.exceptions.ValidationExceptionMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class RestApplication extends Application {
    /**
     * The logger.
     */
    private static final Logger logger = LogManager.getLogger(RestApplication.class);

    /**
     * Provide services and mappers classes for app.
     * @return classes set.
     */
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(EmployeeResource.class);
        classes.add(BasicAuthFilter.class);
        classes.add(EntityNotFoundExceptionMapper.class);
        classes.add(EntityAlreadyExistsExceptionMapper.class);
        classes.add(ValidationExceptionMapper.class);
        classes.add(FieldRequiredExceptionMapper.class);
        return classes;
    }
}
