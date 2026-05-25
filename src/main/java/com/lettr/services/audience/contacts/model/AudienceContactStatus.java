package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

/**
 * Subscription/verification status of an audience contact.
 */
public enum AudienceContactStatus {
    @SerializedName("subscribed") SUBSCRIBED,
    @SerializedName("unsubscribed") UNSUBSCRIBED,
    @SerializedName("bounced") BOUNCED,
    @SerializedName("complained") COMPLAINED,
    @SerializedName("unverified") UNVERIFIED
}
