package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Summary view of a campaign returned by list, send, schedule, and unschedule.
 * Does not include the rendered HTML content; use {@link CampaignDetail}
 * (returned by {@link com.lettr.services.campaigns.Campaigns#get(String)}) for that.
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
