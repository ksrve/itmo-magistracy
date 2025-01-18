package ksrve.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a service entity with essential attributes.
 */
@Data
@Builder
public class Service {
    /**
     * Unique identifier of the business.
     */
    private final String businessKey;

    /**
     * Unique identifier of the service.
     */
    private final String key;

    /**
     * Name of the service.
     */
    private final String name;

    /**
     * Description of the service.
     */
    private final String description;

    /**
     * URL of the service.
     */
    private final String url;
}