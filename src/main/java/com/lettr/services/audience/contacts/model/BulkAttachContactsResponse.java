package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

public class BulkAttachContactsResponse {

    private int attached;

    @SerializedName("already_attached")
    private int alreadyAttached;

    @SerializedName("total_pairs")
    private int totalPairs;

    public int getAttached() { return attached; }
    public int getAlreadyAttached() { return alreadyAttached; }
    public int getTotalPairs() { return totalPairs; }

    @Override
    public String toString() {
        return "BulkAttachContactsResponse{attached=" + attached
                + ", alreadyAttached=" + alreadyAttached
                + ", totalPairs=" + totalPairs + '}';
    }
}
