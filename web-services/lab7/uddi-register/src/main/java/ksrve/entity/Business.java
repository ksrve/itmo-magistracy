package ksrve.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a business entity with essential attributes.
 */
@Data
@Builder
public class Business {
    /**
     * Unique identifier for the business.
     */
    private final String key;

    /**
     * Name of the business.
     */
    private final String name;

    /**
     * Description of the business.
     */
    private final String description;
}