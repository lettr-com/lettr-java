package com.lettr.services.domains.model;

import javax.annotation.Nonnull;

/**
 * Options for creating a new sending domain.
 */
public class CreateDomainOptions {

    private final String domain;

    private CreateDomainOptions(String domain) {
        this.domain = domain;
    }

    /**
     * <b>(required)</b> Create options for registering a new sending domain.
     * Max length: 255 characters. Must match a valid domain pattern.
     *
     * @param domain the domain name (e.g. "example.com")
     * @return CreateDomainOptions instance
     * @throws IllegalArgumentException if {@code domain} is null or empty
     */
    @Nonnull
    public static CreateDomainOptions of(@Nonnull String domain) {
        if (domain == null || domain.isEmpty()) {
            throw new IllegalArgumentException("domain is required");
        }
        return new CreateDomainOptions(domain);
    }

    @Nonnull public String getDomain() { return domain; }
}
