package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Response returned after successfully queuing an email for delivery.
 */
public class CreateEmailResponse {

    @SerializedName("request_id")
    private String requestId;

    private int accepted;
    private int rejected;

    /**
     * Not deserialized - set from the {@code Idempotency-Replayed} response
     * header, which is where the API reports a replay.
     */
    private transient boolean replayed;

    /** Unique identifier for this email transmission. Use it to retrieve the email status later. */
    @Nonnull
    public String getRequestId() { return requestId; }

    /** Number of recipients accepted for delivery. */
    public int getAccepted() { return accepted; }

    /** Number of recipients rejected. */
    public int getRejected() { return rejected; }

    /**
     * Whether this response replayed an earlier send under the same idempotency
     * key.
     *
     * <p>True means no second email went out. It is still a success, not an
     * error — the API hands back the original transmission.
     *
     * <p>Always false for a send made without an idempotency key: there is
     * nothing to replay.
     */
    public boolean isReplayed() { return replayed; }

    /** @hidden set by the SDK from the response header */
    public void setReplayed(boolean replayed) { this.replayed = replayed; }

    @Override
    public String toString() {
        return "CreateEmailResponse{" +
                "requestId='" + requestId + '\'' +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                '}';
    }
}
