package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

/**
 * Type of campaign engagement event. Wire values are defined by {@code @SerializedName};
 * use {@link com.lettr.core.util.WireValues#of(Enum)} to retrieve them.
 */
public enum CampaignEventType {
    @SerializedName("injection") INJECTION,
    @SerializedName("delivery") DELIVERY,
    @SerializedName("bounce") BOUNCE,
    @SerializedName("spam_complaint") SPAM_COMPLAINT,
    @SerializedName("open") OPEN,
    @SerializedName("click") CLICK,
    @SerializedName("list_unsubscribe") LIST_UNSUBSCRIBE
}
