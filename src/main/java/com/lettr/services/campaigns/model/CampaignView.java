package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a single campaign as returned by the API, with embedded engagement
 * stats. The {@code htmlContent} field is only populated by
 * {@link com.lettr.services.campaigns.Campaigns#get(String)}; it is {@code null}
 * on list, send, schedule, and unschedule responses.
 */
public class CampaignView {

    private String id;
    private String name;
    private String subject;

    @SerializedName("from_email")
    private String fromEmail;

    @SerializedName("from_name")
    private String fromName;

    @SerializedName("reply_to")
    private String replyTo;

    private CampaignStatus status;

    @SerializedName("scheduled_at")
    private String scheduledAt;

    @SerializedName("total_recipients")
    private Integer totalRecipients;

    @SerializedName("sent_count")
    private int sentCount;

    @SerializedName("sent_at")
    private String sentAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("html_content")
    private String htmlContent;

    private CampaignStats stats;

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nullable
    public String getSubject() {
        return subject;
    }

    @Nullable
    public String getFromEmail() {
        return fromEmail;
    }

    @Nullable
    public String getFromName() {
        return fromName;
    }

    @Nullable
    public String getReplyTo() {
        return replyTo;
    }

    @Nonnull
    public CampaignStatus getStatus() {
        return status;
    }

    @Nullable
    public String getScheduledAt() {
        return scheduledAt;
    }

    @Nullable
    public Integer getTotalRecipients() {
        return totalRecipients;
    }

    public int getSentCount() {
        return sentCount;
    }

    @Nullable
    public String getSentAt() {
        return sentAt;
    }

    @Nonnull
    public String getCreatedAt() {
        return createdAt;
    }

    /** Rendered HTML content. Only present on {@code get}; {@code null} otherwise. */
    @Nullable
    public String getHtmlContent() {
        return htmlContent;
    }

    @Nonnull
    public CampaignStats getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return "CampaignView{id='" + id + "', name='" + name + "', status=" + status
                + ", sentCount=" + sentCount + '}';
    }
}
