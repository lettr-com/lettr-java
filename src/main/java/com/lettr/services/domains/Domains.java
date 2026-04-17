package com.lettr.services.domains;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.domains.model.CreateDomainOptions;
import com.lettr.services.domains.model.CreateDomainResponse;
import com.lettr.services.domains.model.Domain;
import com.lettr.services.domains.model.ListDomainsResponse;
import com.lettr.services.domains.model.VerifyDomainResponse;

import javax.annotation.Nonnull;

/**
 * Service for managing sending domains via the Lettr API.
 */
public class Domains extends BaseService {

    public Domains(@Nonnull String apiKey) {
        super(apiKey);
    }

    /** List all sending domains. */
    @Nonnull
    public ListDomainsResponse list() throws LettrException {
        return httpClient.get("/domains", null, ListDomainsResponse.class);
    }

    /**
     * Get details of a specific domain.
     *
     * @param domain the domain name (e.g. "example.com")
     * @throws IllegalArgumentException if {@code domain} is null or empty
     */
    @Nonnull
    public Domain get(@Nonnull String domain) throws LettrException {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain is required");
        }
        return httpClient.get("/domains/" + domain, null, Domain.class);
    }

    /** Create a new sending domain. */
    @Nonnull
    public CreateDomainResponse create(@Nonnull CreateDomainOptions options) throws LettrException {
        return httpClient.post("/domains", options, CreateDomainResponse.class);
    }

    /**
     * Delete a sending domain.
     *
     * @throws IllegalArgumentException if {@code domain} is null or empty
     */
    public void delete(@Nonnull String domain) throws LettrException {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain is required");
        }
        httpClient.delete("/domains/" + domain);
    }

    /**
     * Verify a domain's DNS configuration (DKIM, CNAME, DMARC, SPF).
     *
     * @throws IllegalArgumentException if {@code domain} is null or empty
     */
    @Nonnull
    public VerifyDomainResponse verify(@Nonnull String domain) throws LettrException {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain is required");
        }
        return httpClient.post("/domains/" + domain + "/verify", null, VerifyDomainResponse.class);
    }
}
