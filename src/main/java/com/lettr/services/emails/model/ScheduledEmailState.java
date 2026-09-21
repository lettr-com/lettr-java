package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

/**
 * Lifecycle state of a scheduled email.
 *
 * <p>Lettr now owns the schedule itself and only hands the email to the sending
 * provider once it is due, so these are Lettr's own states - not the provider's
 * transmission states. A scheduled email never reports {@code delivered} or
 * {@code bounced}: once it is {@link #SENT} the per-recipient outcome lives on
 * the delivery events, keyed by
 * {@link ScheduledEmail#getTransmissionId() transmissionId}.
 *
 * <p>Wire values are defined by {@code @SerializedName}; use
 * {@link com.lettr.core.util.WireValues#of(Enum)} to retrieve them.
 */
public enum ScheduledEmailState {
    @SerializedName("scheduled") SCHEDULED,
    @SerializedName("sending") SENDING,
    @SerializedName("sent") SENT,
    @SerializedName("cancelled") CANCELLED,
    @SerializedName("failed") FAILED;

    /**
     * Whether a cancel would still stop the email going out.
     *
     * <p>Only {@link #SCHEDULED} qualifies: from {@link #SENDING} onwards the
     * email is already with the provider, which no longer accepts a recall.
     * This is a local pre-check, not a guarantee - the state you are holding
     * was read at some earlier moment, so a cancel can still lose the race and
     * fail.
     */
    public boolean isCancellable() {
        return this == SCHEDULED;
    }

    /**
     * Whether the state can still change. Polling a terminal state will never
     * report anything new.
     */
    public boolean isTerminal() {
        return this == SENT || this == CANCELLED || this == FAILED;
    }
}
