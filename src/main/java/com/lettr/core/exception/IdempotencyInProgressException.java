package com.lettr.core.exception;

/**
 * The original request for this {@code Idempotency-Key} is still being
 * processed (HTTP 409, {@code idempotency_in_progress}).
 *
 * <p>Unlike {@link IdempotencyConflictException} this one <b>is</b> retryable,
 * and must be retried with the <i>same</i> key — a fresh key would send a
 * second email. Wait {@link #getRetryAfter()} seconds first.
 */
public class IdempotencyInProgressException extends LettrApiException {

    private final Integer retryAfter;

    public IdempotencyInProgressException(String message, int statusCode, String errorCode, Integer retryAfter) {
        super(message, statusCode, errorCode);
        this.retryAfter = retryAfter;
    }

    /**
     * Seconds to wait before retrying, from the {@code Retry-After} header.
     *
     * @return the wait in seconds, or null when the API did not send one
     */
    public Integer getRetryAfter() {
        return retryAfter;
    }
}
