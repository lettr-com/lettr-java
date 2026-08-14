package com.lettr.core.exception;

import javax.annotation.Nullable;

/**
 * Thrown when creating an audience contact whose email is already in the team's
 * audience (HTTP 409, {@code resource_already_exists}).
 *
 * <p>This is a client-correctable condition, not an outage — <b>do not retry
 * it.</b> Update the existing contact with {@code audience().contacts().update()},
 * or use {@code bulkCreate()} with {@code updateExisting(true)}.
 *
 * <p>Older API versions surfaced this as an HTTP 500 with the misleading
 * {@code send_error} code, which arrived as a plain {@link LettrApiException}.
 * Extending {@link LettrApiException} keeps existing
 * {@code catch (LettrApiException)} and {@code catch (LettrException)} handlers
 * working unchanged.
 */
public class ContactAlreadyExistsException extends LettrApiException {

    private final String email;

    public ContactAlreadyExistsException(String message, int statusCode, String errorCode, String email) {
        super(message, statusCode, errorCode);
        this.email = email;
    }

    /**
     * The address that collided, when the SDK knows it.
     *
     * @return the submitted email address, or null
     */
    @Nullable
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ContactAlreadyExistsException{" +
                "message='" + getMessage() + '\'' +
                ", statusCode=" + getStatusCode() +
                ", errorCode='" + getErrorCode() + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
