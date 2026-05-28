package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;
import com.lettr.core.util.Args;

import javax.annotation.Nonnull;

/**
 * Request body for scheduling (or rescheduling) a campaign.
 */
public class ScheduleCampaignOptions {

    @SerializedName("scheduled_at")
    private final String scheduledAt;

    private ScheduleCampaignOptions(String scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    /**
     * @param scheduledAt future delivery time (ISO 8601). Include a timezone offset
     *                    (e.g. {@code +02:00} or {@code Z}); a value without an offset
     *                    is interpreted as UTC. Must be in the future.
     */
    @Nonnull
    public static ScheduleCampaignOptions of(@Nonnull String scheduledAt) {
        return new ScheduleCampaignOptions(Args.requireNonEmpty("scheduledAt", scheduledAt));
    }

    @Nonnull
    public String getScheduledAt() {
        return scheduledAt;
    }
}
