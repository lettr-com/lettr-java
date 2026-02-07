package com.lettr.services.domains.model;

/**
 * Options for creating a new sending domain.
 */
public class CreateDomainOptions {

    private final String domain;

    private CreateDomainOptions(String domain) {
        this.domain = domain;
    }

    /**
     * Create options for registering a new sending domain.
     *
     * @param domain the domain name (e.g. "example.com")
     * @return CreateDomainOptions instance
     */
    public static CreateDomainOptions of(String domain) {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain is required");
        }
        return new CreateDomainOptions(domain);
    }

    public String getDomain() {
        return domain;
    }
}
