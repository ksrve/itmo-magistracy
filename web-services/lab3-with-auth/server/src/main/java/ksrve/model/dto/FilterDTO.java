package ksrve.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) class for representing a filter criterion.
 * This class is used to encapsulate key, operation, and value for filtering
 * operations in a user-friendly manner.
 * <p>
 * The FilterDTO is serializable, enabling it to be easily transmitted
 * across network protocols or stored in a session.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterDTO implements Serializable {

    /**
     * The key associated with the filter.
     * This string represents the field or property name that the filter
     * will be applied to.
     */
    private String key;

    /**
     * The operation to be performed on the key.
     * This string specifies the type of filtering operation (e.g.,
     * equals, contains, greater than) that should be applied based on
     * the key.
     */
    private String operation;

    /**
     * The value to be compared against the key during filtering.
     * This string holds the actual data that will be used in conjunction
     * with the key and operation to filter a dataset.
     */
    private String value;
}