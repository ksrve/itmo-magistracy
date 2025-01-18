package ksrve.proxy;

import ksrve.proxy.model.dto.EmployeeUpdateDTO;
import lombok.SneakyThrows;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ksrve.proxy.model.dto.EmployeeDTO;
import ksrve.proxy.model.dto.FilterDTO;

import java.util.List;

/**
 * Class for employee API.
 */
@Path("api/v1/employee")
public interface EmployeeApi {

    /**
     * Find city paginated list by filter.
     *
     * @param filter filter data.
     * @return list of employee entities
     */
    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    List<EmployeeDTO> find(List<FilterDTO> filter);

    /**
     * Creates a employee.
     *
     * @param employee creation info DTO.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    Integer create(EmployeeDTO employee);

    /**
     * Update employee by id with full-data of not entity.
     *
     * @param id employee identifier.
     * @param employee new employee data.
     * @return updated employee.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    EmployeeDTO update(@PathParam("id") final Integer id, EmployeeUpdateDTO employee);

    /**
     * Delete employee by id.
     *
     * @param id The employee identifier.
     */
    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") final Integer id);
}
