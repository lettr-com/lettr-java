package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

public class BulkDetachContactsResponse {

    private int detached;

    @SerializedName("not_present")
    private int notPresent;

    @SerializedName("total_pairs")
    private int totalPairs;

    public int getDetached() { return detached; }
    public int getNotPresent() { return notPresent; }
    public int getTotalPairs() { return totalPairs; }

    @Override
    public String toString() {
        return "BulkDetachContactsResponse{detached=" + detached
                + ", notPresent=" + notPresent
                + ", totalPairs=" + totalPairs + '}';
    }
}
