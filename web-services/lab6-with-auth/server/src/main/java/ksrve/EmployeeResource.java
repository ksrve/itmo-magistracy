package ksrve;

import lombok.SneakyThrows;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ksrve.model.dto.EmployeeDTO;
import ksrve.model.dto.EmployeeUpdateDTO;
import ksrve.model.dto.FilterDTO;

import ksrve.service.EmployeeService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


@Path("/employee")
@RequestScoped
public class EmployeeResource {
    /**
     * The logger.
     */
    private static final Logger logger = LogManager.getLogger(EmployeeResource.class);

    /**
     * The employee service.
     */
    @Inject
    private EmployeeService employeeService;

    /**
     * Find city paginated list by filter.
     *
     * @param filter filter data.
     * @return list of employee entities
     */
    @POST
    @Path("/search")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EmployeeDTO> find(List<FilterDTO> filter) {
        return employeeService.find(filter);
    }

    /**
     * Creates a employee.
     *
     * @param employee creation info DTO.
     * @return id of created employee.
     */
    @POST
    @Path("/")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(EmployeeDTO employee) {
        Integer createdEmployeeId = employeeService.create(employee);
        return Response.ok(createdEmployeeId).build();
    }

    /**
     * Update employee by id with full-data of not entity.
     *
     * @param id employee identifier.
     * @param employee new employee data.
     * @return updated employee.
     */
    @PUT
    @Path("/{id}")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public EmployeeDTO update(@PathParam("id") final Integer id, EmployeeUpdateDTO employee) {
        return employeeService.update(id, employee);
    }

    /**
     * Delete employee by id.
     *
     * @param id The employee identifier.
     * @return OK if deleted.
     */
    @DELETE
    @Path("/{id}")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final Integer id) {
        employeeService.delete(id);
        return Response.ok().build();
    }
}
