package ksrve.common;

import com.beust.jcommander.ParameterException;
import ksrve.Args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ArgsValidator {

    private static final Pattern DATA_PATTERN = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*=[^=]*");

    public HashMap<String, String> validateDataFormat(List<String> data) throws ParameterException {
        HashMap<String, String> result = new HashMap<>();

        for (String pair : data) {
            if (!DATA_PATTERN.matcher(pair).matches()) {
                throw new ParameterException(("Parameter %s should be in the format 'key=value'." +
                        "For example: firstName=Lucas, lastName=Lee, email=lucas.lee@example.com").formatted(pair));
            }
            String[] keyValue = pair.split("=", 2);
            String key = keyValue[0].trim();
            String valuePart = keyValue[1].trim();

            result.put(key, valuePart);
        }

        return result;
    }

    private void validateRequiredParameters(HashMap<String, String> parameters, List<String> requiredParams)
            throws ParameterException {
        for (String requiredParam : requiredParams) {
            if (!parameters.containsKey(requiredParam)) {
                throw new ParameterException("Missing required parameter: " + requiredParam);
            }
        }
    }

    private void validateParameters(HashMap<String, String> parameters, List<String> params)
            throws ParameterException {
        for (String key : parameters.keySet()) {
            if (!params.contains(key)) {
                throw new ParameterException("Parameter is unknown: " + key);
            }
        }
    }

    private HashMap<String, String> validateDiscover(List<String> data, String type) throws ParameterException {
        if (data == null) throw new ParameterException("The following options are required for register: [--data]");
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap = validateDataFormat(data);
        if (Objects.equals(type, "service")) {
            List<String> requiredParams = Arrays.asList("serviceName", "businessName");
            validateRequiredParameters(dataMap, requiredParams);

            List<String> allParams = Arrays.asList("serviceName", "businessName");
            validateParameters(dataMap, allParams);
        } else if (Objects.equals(type, "business")){
            List<String> requiredParams = List.of("businessName");
            validateRequiredParameters(dataMap, requiredParams);

            List<String> allParams = List.of("businessName");
            validateParameters(dataMap, allParams);
        }
        return dataMap;
    }

    private HashMap<String, String> validateRegister(List<String> data, String type) throws ParameterException{
        if (data == null) throw new ParameterException("The following options are required for register: [--data]");
        HashMap<String, String> dataMap = validateDataFormat(data);

        if (Objects.equals(type, "service")) {
            List<String> requiredParams = Arrays.asList("serviceName", "businessName");
            validateRequiredParameters(dataMap, requiredParams);

            List<String> allParams = Arrays.asList("serviceName", "serviceDescription", "serviceUrl", "businessName");
            validateParameters(dataMap, allParams);
        } else if (Objects.equals(type, "business")){
            List<String> requiredParams = List.of("businessName");
            validateRequiredParameters(dataMap, requiredParams);

            List<String> allParams = Arrays.asList("businessName", "businessDescription");
            validateParameters(dataMap, allParams);
        }

        return dataMap;
    }

    public HashMap<String, String> validate(Args args) throws ParameterException {
        HashMap<String, String> dataMap = new HashMap<>();
        if (Objects.equals(args.getCommand(), "register")) {
            dataMap = validateRegister(args.getData(), args.getType());
        } else if (Objects.equals(args.getCommand(), "discover")) {
            dataMap = validateDiscover(args.getData(), args.getType());
        }
        return dataMap;
    }
}
