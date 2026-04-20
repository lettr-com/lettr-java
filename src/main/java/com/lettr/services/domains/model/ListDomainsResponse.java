package com.lettr.services.domains.model;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Response from listing domains.
 */
public class ListDomainsResponse {

    private List<DomainListItem> domains;

    /** Returns the list of registered sending domains. */
    @Nonnull public List<DomainListItem> getDomains() { return domains; }

    @Override
    public String toString() {
        return "ListDomainsResponse{domains=" + domains + '}';
    }
}
