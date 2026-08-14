package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A row that was skipped during a bulk create, with its position in the
 * submitted list.
 *
 * <p>The request still succeeds with HTTP 201 when rows are skipped — check
 * {@link BulkCreateAudienceContactsResponse#hasErrors()} rather than the status.
 */
public class BulkAudienceContactError {

    private int index;

    private String email;

    @SerializedName("error_code")
    private String errorCode;

    private String error;

    /** Zero-based position of the row in the submitted list. */
    public int getIndex() { return index; }

    @Nullable public String getEmail() { return email; }

    /**
     * The raw {@code error_code}. Kept as a {@code String} so a code added
     * server-side is still readable here; see {@link #getCode()} for the typed
     * form.
     */
    @Nullable public String getErrorCode() { return errorCode; }

    /**
     * The typed error code, or {@code null} when the API reported one this SDK
     * version does not know.
     */
    @Nullable
    public BulkAudienceContactErrorCode getCode() {
        return BulkAudienceContactErrorCode.fromWire(errorCode);
    }

    /** The human-readable reason the row was skipped. */
    @Nullable public String getError() { return error; }

    @Override
    @Nonnull
    public String toString() {
        return "BulkAudienceContactError{index=" + index
                + ", email='" + email + '\''
                + ", errorCode='" + errorCode + '\''
                + ", error='" + error + '\'' + '}';
    }
}
