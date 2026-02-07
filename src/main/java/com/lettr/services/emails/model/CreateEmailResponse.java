package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

/**
 * Response returned after successfully queuing an email for delivery.
 */
public class CreateEmailResponse {

    @SerializedName("request_id")
    private String requestId;

    private int accepted;
    private int rejected;

    /**
     * Returns the unique request ID for this email transmission.
     * Use this ID to retrieve the email status later.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Returns the number of accepted recipients.
     */
    public int getAccepted() {
        return accepted;
    }

    /**
     * Returns the number of rejected recipients.
     */
    public int getRejected() {
        return rejected;
    }

    @Override
    public String toString() {
        return "CreateEmailResponse{" +
                "requestId='" + requestId + '\'' +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                '}';
    }
}
