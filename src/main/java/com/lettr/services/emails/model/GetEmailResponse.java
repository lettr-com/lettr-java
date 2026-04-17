package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Response from retrieving details of a specific email transmission.
 */
public class GetEmailResponse {

    @SerializedName("transmission_id")
    private String transmissionId;

    private String state;

    @SerializedName("scheduled_at")
    private String scheduledAt;

    private String from;

    @SerializedName("from_name")
    private String fromName;

    private String subject;

    private List<String> recipients;

    @SerializedName("num_recipients")
    private int numRecipients;

    private List<EmailEvent> events;

    @Nonnull public String getTransmissionId() { return transmissionId; }

    /** Derived delivery state: {@code scheduled}, {@code delivered}, {@code bounced}, or {@code failed}. */
    @Nonnull public String getState() { return state; }

    /** Scheduled delivery time. Usually null for immediately-sent emails. */
    @Nullable public String getScheduledAt() { return scheduledAt; }

    @Nonnull public String getFrom() { return from; }
    @Nullable public String getFromName() { return fromName; }
    @Nonnull public String getSubject() { return subject; }
    @Nonnull public List<String> getRecipients() { return recipients; }
    public int getNumRecipients() { return numRecipients; }
    @Nonnull public List<EmailEvent> getEvents() { return events; }

    @Override
    public String toString() {
        return "GetEmailResponse{" +
                "transmissionId='" + transmissionId + '\'' +
                ", state='" + state + '\'' +
                ", subject='" + subject + '\'' +
                ", numRecipients=" + numRecipients +
                '}';
    }
}
