package com.lettr.services.emails;

import com.lettr.core.exception.LettrException;
import com.lettr.core.net.HttpClient;
import com.lettr.core.util.Args;
import com.lettr.core.util.IdempotencyKeys;
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
     * Send an email under an idempotency key.
     *
     * <p>Reuse the key when you retry and the API returns the original result
     * instead of delivering a second email;
     * {@link CreateEmailResponse#isReplayed()} says when that happened.
     *
     * <p><b>You choose the key; the SDK never generates one.</b> It only works
     * if both attempts use the same value, and the SDK does not retry — one
     * {@code send()} is one HTTP request — so the retry is yours, and only you
     * know that two calls are the same logical send.
     *
     * @param options        the email to send
     * @param idempotencyKey 1–255 characters of {@code [A-Za-z0-9._-]}
     * @throws IllegalArgumentException if the key is malformed — thrown before
     *                                  any request is made
     * @throws com.lettr.core.exception.IdempotencyInProgressException if the
     *         original send is still running; retry with the <b>same</b> key
     * @throws com.lettr.core.exception.IdempotencyConflictException if the key
     *         was used with a different payload; do not retry
     * @throws LettrException if the request fails
     */
    @Nonnull
    public CreateEmailResponse send(@Nonnull CreateEmailOptions options,
                                    @Nonnull String idempotencyKey) throws LettrException {
        // Checked here so a malformed key fails locally instead of costing a
        // round trip and a 422.
        IdempotencyKeys.validate(idempotencyKey);

        HttpClient.ApiResponse<CreateEmailResponse> response = httpClient.postWithHeaders(
                "/emails",
                options,
                CreateEmailResponse.class,
                Map.of("Idempotency-Key", idempotencyKey));

        CreateEmailResponse data = response.getData();
        data.setReplayed("true".equalsIgnoreCase(response.header("Idempotency-Replayed")));
        return data;
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
        Args.requireNonEmpty("requestId", requestId);
        Map<String, String> params = null;
        if (from != null || to != null) {
            params = new LinkedHashMap<>();
            if (from != null) params.put("from", from);
            if (to != null) params.put("to", to);
        }
        return httpClient.get("/emails/" + HttpClient.encodePathSegment(requestId), params, GetEmailResponse.class);
    }

    /**
     * Schedule an email for future delivery.
     *
     * @param options schedule email options including {@code scheduledAt}, which
     *                must be 5 minutes to 30 days in the future
     * @return the scheduled email, including the {@code sch_} request ID that
     *         addresses it
     * @throws LettrException if the request fails
     */
    @Nonnull
    public ScheduledEmail schedule(@Nonnull ScheduleEmailOptions options) throws LettrException {
        return httpClient.post("/emails/scheduled", options, ScheduledEmail.class);
    }

    /**
     * List scheduled emails with optional filtering and pagination.
     *
     * @param params optional query parameters; pass null for defaults
     * @return a page of scheduled emails with pagination metadata
     * @throws LettrException if the request fails
     */
    @Nonnull
    public ListScheduledEmailsResponse listScheduled(@Nullable ListScheduledEmailsParams params) throws LettrException {
        return httpClient.get("/emails/scheduled", params != null ? params.toQueryParams() : null,
                ListScheduledEmailsResponse.class);
    }

    /** List scheduled emails with default pagination. */
    @Nonnull
    public ListScheduledEmailsResponse listScheduled() throws LettrException {
        return listScheduled(null);
    }

    /**
     * Get a scheduled email.
     *
     * @param requestId the {@code sch_} request ID from
     *                  {@link #schedule(ScheduleEmailOptions)} — <b>not</b> the
     *                  provider transmission ID that webhook events carry
     * @return the scheduled email
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code requestId} is null or empty
     */
    @Nonnull
    public ScheduledEmail getScheduled(@Nonnull String requestId) throws LettrException {
        Args.requireNonEmpty("requestId", requestId);
        return withRequestId(
                httpClient.get("/emails/scheduled/" + HttpClient.encodePathSegment(requestId), null, ScheduledEmail.class),
                requestId);
    }

    /**
     * Cancel a scheduled email. Only an email that has not yet been handed to
     * the sending provider can be cancelled; see
     * {@link ScheduledEmailState#isCancellable()}.
     *
     * @param requestId the {@code sch_} request ID to cancel — <b>not</b> the
     *                  provider transmission ID that webhook events carry
     * @return the cancelled email, in state
     *         {@link ScheduledEmailState#CANCELLED} and with {@code accepted} back to 0
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code requestId} is null or empty
     */
    @Nonnull
    public ScheduledEmail cancelScheduled(@Nonnull String requestId) throws LettrException {
        Args.requireNonEmpty("requestId", requestId);
        return withRequestId(
                httpClient.delete("/emails/scheduled/" + HttpClient.encodePathSegment(requestId), ScheduledEmail.class),
                requestId);
    }

    /**
     * Emails scheduled before Lettr owned the schedule are still addressed by
     * their provider transmission ID, and the API answers those from delivery
     * events in a payload that has no {@code request_id} at all. Filling it in
     * keeps {@code getRequestId()} the id that addresses the email, whichever
     * era it comes from.
     */
    @Nonnull
    private ScheduledEmail withRequestId(@Nonnull ScheduledEmail email, @Nonnull String requestId) {
        if (email.getRequestId() == null) {
            email.setRequestId(requestId);
        }
        return email;
    }
}
