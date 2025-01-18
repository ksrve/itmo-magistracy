package ksrve.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents an object for searching within a UDDI registry.
 * This class encapsulates the search criteria used for
 * querying UDDI (Universal Description, Discovery and Integration)
 * services.
 *
 * <p>
 * A {@code SearchObject} contains a specified {@code type},
 * a list of search qualifiers, and a string type that helps
 * define the nature of the search.
 * </p>
 *
 * <p>
 * Note: This class is primarily intended for use with the
 * UDDI client library, and the search criteria should align
 * with UDDI specifications.
 * </p>
 */
@Data
@Builder
public class SearchObject {

    /**
     * A list of qualifiers that further refine the search.
     */
    private List<String> qualifiers;

    /**
     * The string for the search.
     */
    private String searchString;
}