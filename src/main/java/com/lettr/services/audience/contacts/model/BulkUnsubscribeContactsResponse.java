package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

/**
 * Counts from a bulk topic unsubscribe over {@code contactIds} × {@code topicIds}.
 *
 * <p>Pairs that did not exist are ignored, so {@link #getUnsubscribed()} can be
 * lower than {@link #getTotalPairs()}.
 */
public class BulkUnsubscribeContactsResponse {

    private int unsubscribed;

    @SerializedName("total_pairs")
    private int totalPairs;

    public int getUnsubscribed() { return unsubscribed; }
    public int getTotalPairs() { return totalPairs; }

    @Override
    public String toString() {
        return "BulkUnsubscribeContactsResponse{unsubscribed=" + unsubscribed
                + ", totalPairs=" + totalPairs + '}';
    }
}
