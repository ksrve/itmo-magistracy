package ksrve.util;


import org.uddi.api_v3.*;

import java.util.List;

/**
 * Utility class for printing information about businesses and their services.
 * This class handles the presentation layer for business and service data,
 * providing formatted output for clarity in understanding the data.
 */
public class EntityPrinter {

    /**
     * Prints a list of BusinessInfo objects to the console.
     *
     * @param businessInfos the list of BusinessInfo objects to print
     */
    public void printBusinessInfos(List<BusinessInfo> businessInfos) {
        if (businessInfos == null || businessInfos.isEmpty()) {
            System.out.println("No data returned");
        } else {
            System.out.println("######################################################");
            for (BusinessInfo businessInfo : businessInfos) {
                System.out.println("===============================================");
                System.out.println("Business Key: " + businessInfo.getBusinessKey());
                System.out.println("Name: " + listToString(businessInfo.getName()));
                System.out.println("Description: " + listToDescString(businessInfo.getDescription()));
                System.out.println("Services:");
                if (businessInfo.getServiceInfos() != null) {
                    printServiceInfos(businessInfo.getServiceInfos().getServiceInfo());
                }
            }
            System.out.println();
        }
    }

    /**
     * Prints the service information associated with BusinessInfo.
     *
     * @param serviceInfos the ServiceInfos object containing service details
     */
    public void printServiceInfos(List<ServiceInfo> serviceInfos) {
        if (serviceInfos == null || serviceInfos.isEmpty()) {
            System.out.println("No data returned");
        } else {
            System.out.println("######################################################");
            for (ServiceInfo serviceInfo : serviceInfos) {
                System.out.println("-------------------------------------------");
                System.out.println("Service Key: " + serviceInfo.getServiceKey());
                System.out.println("Owning Business Key: " + serviceInfo.getBusinessKey());
                System.out.println("Name: " + listToString(serviceInfo.getName()));
            }
        }
    }

    /**
     * Converts a list of Name objects to a single space-separated string.
     *
     * @param names the list of Name objects
     * @return a combined string of all Name values
     */
    private String listToString(List<Name> names) {
        StringBuilder sb = new StringBuilder();
        for (Name name : names) {
            sb.append(name.getValue()).append(" ");
        }
        return sb.toString().trim(); // Trim to remove any trailing spaces
    }

    /**
     * Converts a list of Description objects to a single space-separated string.
     *
     * @param descriptions the list of Description objects
     * @return a combined string of all Description values
     */
    private String listToDescString(List<Description> descriptions) {
        StringBuilder sb = new StringBuilder();
        for (Description description : descriptions) {
            sb.append(description.getValue()).append(" ");
        }
        return sb.toString().trim(); // Trim to remove any trailing spaces
    }

}