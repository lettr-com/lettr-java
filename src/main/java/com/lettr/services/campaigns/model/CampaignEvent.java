package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A single campaign engagement event (open, click, bounce, etc.).
 */
public class CampaignEvent {

    @SerializedName("event_id")
    private String eventId;

    @SerializedName("event_type")
    private CampaignEventType eventType;

    private String email;
    private String timestamp;

    @SerializedName("bounce_class")
    private String bounceClass;

    private String reason;

    @SerializedName("target_link_url")
    private String targetLinkUrl;

    @SerializedName("user_agent")
    private String userAgent;

    @Nonnull
    public String getEventId() {
        return eventId;
    }

    @Nonnull
    public CampaignEventType getEventType() {
        return eventType;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    @Nonnull
    public String getTimestamp() {
        return timestamp;
    }

    /** SparkPost bounce classification code; only set on bounce events. */
    @Nullable
    public String getBounceClass() {
        return bounceClass;
    }

    /** Failure or bounce reason, if any. */
    @Nullable
    public String getReason() {
        return reason;
    }

    /** Clicked link URL; only set on click events. */
    @Nullable
    public String getTargetLinkUrl() {
        return targetLinkUrl;
    }

    /** Recipient user agent; only set on open/click events. */
    @Nullable
    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "CampaignEvent{eventId='" + eventId + "', eventType=" + eventType
                + ", email='" + email + "', timestamp='" + timestamp + "'}";
    }
}
