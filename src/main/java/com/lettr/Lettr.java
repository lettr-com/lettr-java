package com.lettr;

import com.lettr.services.domains.Domains;
import com.lettr.services.emails.Emails;
import com.lettr.services.projects.Projects;
import com.lettr.services.system.System;
import com.lettr.services.templates.Templates;
import com.lettr.services.webhooks.Webhooks;

import javax.annotation.Nonnull;

/**
 * Main entry point for the Lettr Java SDK.
 *
 * <p>Create an instance with your API key and access services via fluent methods:</p>
 *
 * <pre>{@code
 * Lettr lettr = new Lettr("your-api-key");
 *
 * CreateEmailResponse response = lettr.emails().send(
 *     CreateEmailOptions.builder()
 *         .from("sender@example.com")
 *         .to("recipient@example.com")
 *         .subject("Hello!")
 *         .html("<p>Hello, world!</p>")
 *         .build()
 * );
 * }</pre>
 *
 * @see <a href="https://lettr.com">Lettr Documentation</a>
 */
public class Lettr {

    private final String apiKey;

    /**
     * Creates a new Lettr client with the given API key.
     *
     * @param apiKey your Lettr API key (find it at https://app.lettr.com)
     * @throws IllegalArgumentException if {@code apiKey} is null or empty
     */
    public Lettr(@Nonnull String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is required. Get yours at https://app.lettr.com");
        }
        this.apiKey = apiKey;
    }

    /** Returns the Emails service for sending and retrieving emails. */
    @Nonnull public Emails emails() { return new Emails(apiKey); }

    /** Returns the Domains service for managing sending domains. */
    @Nonnull public Domains domains() { return new Domains(apiKey); }

    /** Returns the Webhooks service for managing webhook configurations. */
    @Nonnull public Webhooks webhooks() { return new Webhooks(apiKey); }

    /** Returns the Templates service for managing email templates. */
    @Nonnull public Templates templates() { return new Templates(apiKey); }

    /** Returns the Projects service for listing projects. */
    @Nonnull public Projects projects() { return new Projects(apiKey); }

    /** Returns the System service for health checks and API key validation. */
    @Nonnull public System system() { return new System(apiKey); }
}
