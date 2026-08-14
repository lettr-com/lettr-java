package com.lettr.services.audience.contacts.model;

import javax.annotation.Nullable;

/**
 * Reason a single row was skipped during a bulk contact create.
 *
 * <p>These are per-row codes reported inside a {@code 201} body — not the
 * top-level {@code error_code} of a failed request.
 *
 * <p>{@link BulkAudienceContactError#getErrorCode()} stays a raw {@code String}
 * so a code added server-side survives; use {@link #fromWire(String)} (or
 * {@link BulkAudienceContactError#getCode()}) to match against these constants,
 * and handle the {@code null} that an unknown code produces.
 */
public enum BulkAudienceContactErrorCode {

    MISSING_EMAIL("missing_email"),
    INVALID_EMAIL("invalid_email"),
    INVALID_PROPERTY_VALUE("invalid_property_value"),
    UNKNOWN_PROPERTY_KEY("unknown_property_key"),
    UNKNOWN_LIST("unknown_list"),
    UNKNOWN_TOPIC("unknown_topic"),
    INVALID_TOPIC_SUBSCRIPTION("invalid_topic_subscription");

    private final String wireValue;

    BulkAudienceContactErrorCode(String wireValue) {
        this.wireValue = wireValue;
    }

    /** The value the API sends on the wire. */
    public String getWireValue() {
        return wireValue;
    }

    /**
     * Resolves a wire value to a constant, or {@code null} when the API reports
     * a code this SDK version does not know.
     */
    @Nullable
    public static BulkAudienceContactErrorCode fromWire(@Nullable String wireValue) {
        if (wireValue == null) {
            return null;
        }
        for (BulkAudienceContactErrorCode code : values()) {
            if (code.wireValue.equals(wireValue)) {
                return code;
            }
        }
        return null;
    }
}
