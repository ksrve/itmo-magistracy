package ksrve.executors.update;

import com.beust.jcommander.JCommander;
import ksrve.executors.common.CommandExecutor;
import ksrve.proxy.EmployeeApiClient;
import ksrve.proxy.model.dto.EmployeeDTO;
import ksrve.proxy.model.dto.EmployeeUpdateDTO;
import ksrve.utils.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class UpdateCommandExecutor implements CommandExecutor {

    private final UpdateArgs args;
    private static final Logger logger = LogManager.getLogger(UpdateCommandExecutor.class);

    public UpdateCommandExecutor() {
        this.args = new UpdateArgs();
    }

    public static EmployeeUpdateDTO parseData(List<String> entries) {
        EmployeeUpdateDTO employee = new EmployeeUpdateDTO();

        for (String entry : entries) {
            String[] keyValue = entry.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                try {
                    switch (key) {
                        case "department":
                            employee.setDepartment(value);
                            break;
                        case "firstName":
                            employee.setFirstName(value);
                            break;
                        case "lastName":
                            employee.setLastName(value);
                            break;
                        case "email":
                            employee.setEmail(value);
                            break;
                        case "hireDate":
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = dateFormat.parse(value);

                            GregorianCalendar gregorianCalendar = new GregorianCalendar();
                            gregorianCalendar.setTime(date);

                            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
                            employee.setHireDate(date);
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    logger.error("Error parsing number for key: {} with value: {}", key, value);
                } catch (Exception e) {
                    logger.error("Error processing entry: {} - {}", entry, e.getMessage());
                }
            }
        }
        return employee;
    }

    @Override
    public void parseArgs(String[] argv){
        JCommander jCommander = JCommander.newBuilder()
                .addObject(args)
                .build();
        jCommander.parse(argv);

    }

    @Override
    public void run(EmployeeApiClient service) {
        var employeeService = service.getApi();
        try {
            EmployeeDTO updatedEmployee = employeeService.update(args.getId(), parseData(args.getData()));
            printer.printData(Collections.singletonList(updatedEmployee));
            logger.info("Successfully updated employee with id {}", args.getId());
        }catch (Exception e){
            ExceptionHandler.handleException(e);
        }
    }
}