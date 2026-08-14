package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

/** Counts from a bulk topic subscribe over {@code contactIds} × {@code topicIds}. */
public class BulkSubscribeContactsResponse {

    private int subscribed;

    @SerializedName("already_subscribed")
    private int alreadySubscribed;

    @SerializedName("total_pairs")
    private int totalPairs;

    public int getSubscribed() { return subscribed; }
    public int getAlreadySubscribed() { return alreadySubscribed; }
    public int getTotalPairs() { return totalPairs; }

    @Override
    public String toString() {
        return "BulkSubscribeContactsResponse{subscribed=" + subscribed
                + ", alreadySubscribed=" + alreadySubscribed
                + ", totalPairs=" + totalPairs + '}';
    }
}
