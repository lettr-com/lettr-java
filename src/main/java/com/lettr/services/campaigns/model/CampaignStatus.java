package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

/**
 * Lifecycle status of a campaign. Wire values are defined by {@code @SerializedName};
 * use {@link com.lettr.core.util.WireValues#of(Enum)} to retrieve them.
 */
public enum CampaignStatus {
    @SerializedName("draft") DRAFT,
    @SerializedName("scheduled") SCHEDULED,
    @SerializedName("preparing") PREPARING,
    @SerializedName("in_review") IN_REVIEW,
    @SerializedName("sending") SENDING,
    @SerializedName("sent") SENT,
    @SerializedName("failed") FAILED
}
