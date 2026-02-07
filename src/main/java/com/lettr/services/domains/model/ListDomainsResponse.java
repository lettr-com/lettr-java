package com.lettr.services.domains.model;

import java.util.List;

/**
 * Response from listing domains.
 */
public class ListDomainsResponse {

    private List<Domain> domains;

    /**
     * Returns the list of registered sending domains.
     */
    public List<Domain> getDomains() {
        return domains;
    }

    @Override
    public String toString() {
        return "ListDomainsResponse{" +
                "domains=" + domains +
                '}';
    }
}
