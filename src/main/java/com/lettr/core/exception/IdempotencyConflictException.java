package com.lettr.core.exception;

/**
 * The {@code Idempotency-Key} was already used with a <i>different</i> request
 * payload (HTTP 409, {@code idempotency_key_conflict}).
 *
 * <p><b>Never retry this.</b> Two different emails were sent under one key,
 * which is a bug on the caller's side; the same request will fail identically
 * forever. Use a key that is unique per logical send, or send the payload the
 * key was first used with.
 *
 * <p>Keys are scoped per team <b>and</b> API key, so the same string sent
 * through a different API key is a different key and will not collide.
 *
 * <p>Extends {@link LettrApiException}, so existing
 * {@code catch (LettrApiException)} handlers keep working unchanged.
 */
public class IdempotencyConflictException extends LettrApiException {

    public IdempotencyConflictException(String message, int statusCode, String errorCode) {
        super(message, statusCode, errorCode);
    }
}
