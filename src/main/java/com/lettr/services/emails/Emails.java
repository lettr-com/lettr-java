package com.lettr.services.emails;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.emails.model.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service for sending, scheduling, and retrieving emails via the Lettr API.
 */
public class Emails extends BaseService {

    public Emails(@Nonnull String apiKey) {
        super(apiKey);
    }

    /**
     * Send a transactional email.
     *
     * @param options email options including from, to, subject, and content
     * @return response containing the request ID and acceptance counts
     * @throws LettrException if the request fails
     */
    @Nonnull
    public CreateEmailResponse send(@Nonnull CreateEmailOptions options) throws LettrException {
        return httpClient.post("/emails", options, CreateEmailResponse.class);
    }

    /**
     * List sent emails with optional filtering and pagination.
     *
     * @param params optional query parameters; pass null for defaults
     * @return paginated list of email events
     * @throws LettrException if the request fails
     */
    @Nonnull
    public ListEmailsResponse list(@Nullable ListEmailsParams params) throws LettrException {
        return httpClient.get("/emails", params != null ? params.toQueryParams() : null, ListEmailsResponse.class);
    }

    /** List sent emails with default pagination. */
    @Nonnull
    public ListEmailsResponse list() throws LettrException {
        return list(null);
    }

    /**
     * List email events with optional filtering and pagination.
     *
     * @param params optional query parameters; pass null for defaults
     * @return paginated list of email events
     * @throws LettrException if the request fails
     */
    @Nonnull
    public ListEmailEventsResponse listEvents(@Nullable ListEmailEventsParams params) throws LettrException {
        return httpClient.get("/emails/events", params != null ? params.toQueryParams() : null, ListEmailEventsResponse.class);
    }

    /** List email events with default parameters. */
    @Nonnull
    public ListEmailEventsResponse listEvents() throws LettrException {
        return listEvents(null);
    }

    /**
     * Get details of a specific email transmission.
     *
     * @param requestId the request ID returned when the email was sent
     * @return response containing transmission details and events
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code requestId} is null or empty
     */
    @Nonnull
    public GetEmailResponse get(@Nonnull String requestId) throws LettrException {
        return get(requestId, null, null);
    }

    /**
     * Get details of a specific email transmission with optional date range filtering.
     *
     * @param requestId the request ID returned when the email was sent
     * @param from      optional start date for the event search range (ISO 8601). Defaults to 10 days ago.
     * @param to        optional end date for the event search range (ISO 8601). Defaults to now.
     * @return response containing transmission details and events
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code requestId} is null or empty
     */
    @Nonnull
    public GetEmailResponse get(@Nonnull String requestId, @Nullable String from, @Nullable String to) throws LettrException {
        if (requestId == null || requestId.isEmpty()) {
            throw new IllegalArgumentException("requestId is required");
        }
        Map<String, String> params = null;
        if (from != null || to != null) {
            params = new LinkedHashMap<>();
            if (from != null) params.put("from", from);
            if (to != null) params.put("to", to);
        }
        return httpClient.get("/emails/" + requestId, params, GetEmailResponse.class);
    }

    /**
     * Schedule an email for future delivery.
     *
     * @param options schedule email options including {@code scheduledAt}
     * @return response containing the request ID and acceptance counts
     * @throws LettrException if the request fails
     */
    @Nonnull
    public CreateEmailResponse schedule(@Nonnull ScheduleEmailOptions options) throws LettrException {
        return httpClient.post("/emails/scheduled", options, CreateEmailResponse.class);
    }

    /**
     * Get details of a scheduled email transmission.
     *
     * @param transmissionId the transmission ID
     * @return scheduled email details
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code transmissionId} is null or empty
     */
    @Nonnull
    public ScheduledEmail getScheduled(@Nonnull String transmissionId) throws LettrException {
        if (transmissionId == null || transmissionId.isEmpty()) {
            throw new IllegalArgumentException("transmissionId is required");
        }
        return httpClient.get("/emails/scheduled/" + transmissionId, null, ScheduledEmail.class);
    }

    /**
     * Cancel a scheduled email.
     *
     * @param transmissionId the transmission ID to cancel
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code transmissionId} is null or empty
     */
    public void cancelScheduled(@Nonnull String transmissionId) throws LettrException {
        if (transmissionId == null || transmissionId.isEmpty()) {
            throw new IllegalArgumentException("transmissionId is required");
        }
        httpClient.delete("/emails/scheduled/" + transmissionId);
    }
}
