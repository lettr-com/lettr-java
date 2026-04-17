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

    /** Unique identifier for this email transmission. Use it to retrieve the email status later. */
    @Nonnull
    public String getRequestId() { return requestId; }

    /** Number of recipients accepted for delivery. */
    public int getAccepted() { return accepted; }

    /** Number of recipients rejected. */
    public int getRejected() { return rejected; }

    @Override
    public String toString() {
        return "CreateEmailResponse{" +
                "requestId='" + requestId + '\'' +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                '}';
    }
}
