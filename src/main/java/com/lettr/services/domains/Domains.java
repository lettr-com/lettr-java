package com.lettr.services.domains;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.domains.model.CreateDomainOptions;
import com.lettr.services.domains.model.CreateDomainResponse;
import com.lettr.services.domains.model.Domain;
import com.lettr.services.domains.model.ListDomainsResponse;

/**
 * Service for managing sending domains via the Lettr API.
 *
 * <p>Example:</p>
 * <pre>{@code
 * Lettr lettr = new Lettr("your-api-key");
 *
 * // List all domains
 * ListDomainsResponse domains = lettr.domains().list();
 *
 * // Create a new domain
 * CreateDomainResponse response = lettr.domains().create(
 *     CreateDomainOptions.of("example.com")
 * );
 * }</pre>
 */
public class Domains extends BaseService {

    public Domains(String apiKey) {
        super(apiKey);
    }

    /**
     * List all sending domains.
     *
     * @return response containing list of domains
     * @throws LettrException if the request fails
     */
    public ListDomainsResponse list() throws LettrException {
        return httpClient.get("/domains", null, ListDomainsResponse.class);
    }

    /**
     * Get details of a specific domain.
     *
     * @param domain the domain name (e.g. "example.com")
     * @return domain details including DNS records
     * @throws LettrException if the request fails
     */
    public Domain get(String domain) throws LettrException {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain is required");
        }
        return httpClient.get("/domains/" + domain, null, Domain.class);
    }

    /**
     * Create a new sending domain.
     *
     * @param options domain creation options
     * @return response containing the domain and DKIM configuration
     * @throws LettrException if the request fails
     */
    public CreateDomainResponse create(CreateDomainOptions options) throws LettrException {
        return httpClient.post("/domains", options, CreateDomainResponse.class);
    }

    /**
     * Delete a sending domain.
     *
     * @param domain the domain name to delete
     * @throws LettrException if the request fails
     */
    public void delete(String domain) throws LettrException {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain is required");
        }
        httpClient.delete("/domains/" + domain);
    }
}
