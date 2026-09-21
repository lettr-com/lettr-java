package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * A scheduled email, as Lettr holds it until its delivery time.
 *
 * <p>Two identifiers, and they are not interchangeable:
 * {@link #getRequestId() requestId} is Lettr's own {@code sch_} id and is the
 * one that addresses this email in {@code getScheduled} / {@code cancelScheduled};
 * {@link #getTransmissionId() transmissionId} is the sending provider's id, is
 * {@code null} until the email actually goes out, and is the value that appears
 * on webhook events.
 */
public class ScheduledEmail {

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("transmission_id")
    private String transmissionId;

    private ScheduledEmailState state;

    @SerializedName("scheduled_at")
    private String scheduledAt;

    private String from;

    @SerializedName("from_name")
    private String fromName;

    private String subject;

    private List<String> recipients;

    @SerializedName("num_recipients")
    private int numRecipients;

    private int accepted;

    private int rejected;

    private String tag;

    @SerializedName("failure_reason")
    private String failureReason;

    private List<EmailEvent> events;

    /**
     * Lettr's identifier for this scheduled email, prefixed {@code sch_}. Pass
     * it to {@code getScheduled()} and {@code cancelScheduled()}.
     */
    @Nonnull public String getRequestId() { return requestId; }

    /**
     * The sending provider's transmission id, or {@code null} while the email is
     * still waiting to be sent - Lettr does not hand it to the provider until it
     * is due. Correlate webhook events with this, not with
     * {@link #getRequestId()}.
     */
    @Nullable public String getTransmissionId() { return transmissionId; }

    /** Current lifecycle state. */
    @Nonnull public ScheduledEmailState getState() { return state; }

    /** Scheduled delivery time in ISO 8601. */
    @Nullable public String getScheduledAt() { return scheduledAt; }

    @Nonnull public String getFrom() { return from; }
    @Nullable public String getFromName() { return fromName; }
    @Nullable public String getSubject() { return subject; }
    @Nonnull public List<String> getRecipients() { return recipients != null ? recipients : Collections.emptyList(); }
    public int getNumRecipients() { return numRecipients; }

    /** Recipients accepted for delivery. Drops to 0 once the email is cancelled. */
    public int getAccepted() { return accepted; }

    /** Recipients rejected at scheduling time. */
    public int getRejected() { return rejected; }

    @Nullable public String getTag() { return tag; }

    /** Why the send failed; only populated in the {@code FAILED} state. */
    @Nullable public String getFailureReason() { return failureReason; }

    /** Empty while the email is still scheduled; populated once it has been sent. */
    @Nonnull public List<EmailEvent> getEvents() { return events != null ? events : Collections.emptyList(); }

    /**
     * @hidden set by the SDK when the API answers from pre-Lettr-scheduling
     *         delivery events, whose payload carries no {@code request_id}
     */
    public void setRequestId(String requestId) { this.requestId = requestId; }

    @Override
    public String toString() {
        return "ScheduledEmail{" +
                "requestId='" + requestId + '\'' +
                ", transmissionId='" + transmissionId + '\'' +
                ", state=" + state +
                ", scheduledAt='" + scheduledAt + '\'' +
                ", subject='" + subject + '\'' +
                ", numRecipients=" + numRecipients +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                ", tag='" + tag + '\'' +
                ", failureReason='" + failureReason + '\'' +
                '}';
    }
}
