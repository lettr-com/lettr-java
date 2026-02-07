package com.lettr.services.emails;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.emails.model.CreateEmailOptions;
import com.lettr.services.emails.model.CreateEmailResponse;
import com.lettr.services.emails.model.GetEmailResponse;
import com.lettr.services.emails.model.ListEmailsParams;
import com.lettr.services.emails.model.ListEmailsResponse;

/**
 * Service for sending and retrieving emails via the Lettr API.
 *
 * <p>Example:</p>
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
 */
public class Emails extends BaseService {

    public Emails(String apiKey) {
        super(apiKey);
    }

    /**
     * Send a transactional email.
     *
     * @param options email options including from, to, subject, and content
     * @return response containing the request ID and acceptance counts
     * @throws LettrException if the request fails
     */
    public CreateEmailResponse send(CreateEmailOptions options) throws LettrException {
        return httpClient.post("/emails", options, CreateEmailResponse.class);
    }

    /**
     * List sent emails with optional filtering and pagination.
     *
     * @param params optional query parameters for filtering
     * @return paginated list of email events
     * @throws LettrException if the request fails
     */
    public ListEmailsResponse list(ListEmailsParams params) throws LettrException {
        return httpClient.get("/emails", params != null ? params.toQueryParams() : null, ListEmailsResponse.class);
    }

    /**
     * List sent emails with default pagination.
     *
     * @return paginated list of email events
     * @throws LettrException if the request fails
     */
    public ListEmailsResponse list() throws LettrException {
        return list(null);
    }

    /**
     * Get all events for a specific email transmission.
     *
     * @param requestId the request ID returned when the email was sent
     * @return response containing all events (injection, delivery, bounce, etc.)
     * @throws LettrException if the request fails
     */
    public GetEmailResponse get(String requestId) throws LettrException {
        if (requestId == null || requestId.isEmpty()) {
            throw new IllegalArgumentException("requestId is required");
        }
        return httpClient.get("/emails/" + requestId, null, GetEmailResponse.class);
    }
}
