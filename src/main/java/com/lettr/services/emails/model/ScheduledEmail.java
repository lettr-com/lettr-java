package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents a scheduled email transmission.
 */
public class ScheduledEmail {

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

    /**
     * Current state: {@code submitted}, {@code generating}, {@code scheduled},
     * {@code delivered}, {@code bounced}, {@code failed}, or {@code unknown}.
     */
    @Nonnull public String getState() { return state; }

    /** Scheduled delivery time. May be null when the transmission has already been delivered. */
    @Nullable public String getScheduledAt() { return scheduledAt; }

    @Nonnull public String getFrom() { return from; }
    @Nullable public String getFromName() { return fromName; }
    @Nonnull public String getSubject() { return subject; }
    @Nonnull public List<String> getRecipients() { return recipients; }
    public int getNumRecipients() { return numRecipients; }

    /** Empty array when the email is still scheduled; populated once processed. */
    @Nonnull public List<EmailEvent> getEvents() { return events; }

    @Override
    public String toString() {
        return "ScheduledEmail{" +
                "transmissionId='" + transmissionId + '\'' +
                ", state='" + state + '\'' +
                ", scheduledAt='" + scheduledAt + '\'' +
                '}';
    }
}
