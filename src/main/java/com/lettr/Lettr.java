package com.lettr;

import com.lettr.services.domains.Domains;
import com.lettr.services.emails.Emails;
import com.lettr.services.templates.Templates;
import com.lettr.services.webhooks.Webhooks;

/**
 * Main entry point for the Lettr Java SDK.
 *
 * <p>Create an instance with your API key and access services via fluent methods:</p>
 *
 * <pre>{@code
 * Lettr lettr = new Lettr("your-api-key");
 *
 * // Send an email
 * CreateEmailResponse response = lettr.emails().send(
 *     CreateEmailOptions.builder()
 *         .from("sender@example.com")
 *         .to("recipient@example.com")
 *         .subject("Hello!")
 *         .html("<p>Hello, world!</p>")
 *         .build()
 * );
 *
 * // List domains
 * ListDomainsResponse domains = lettr.domains().list();
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
     * @throws IllegalArgumentException if apiKey is null or empty
     */
    public Lettr(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is required. Get yours at https://app.lettr.com");
        }
        this.apiKey = apiKey;
    }

    /**
     * Returns the Emails service for sending and retrieving emails.
     *
     * @return Emails service instance
     */
    public Emails emails() {
        return new Emails(apiKey);
    }

    /**
     * Returns the Domains service for managing sending domains.
     *
     * @return Domains service instance
     */
    public Domains domains() {
        return new Domains(apiKey);
    }

    /**
     * Returns the Webhooks service for managing webhook configurations.
     *
     * @return Webhooks service instance
     */
    public Webhooks webhooks() {
        return new Webhooks(apiKey);
    }

    /**
     * Returns the Templates service for managing email templates.
     *
     * @return Templates service instance
     */
    public Templates templates() {
        return new Templates(apiKey);
    }
}
