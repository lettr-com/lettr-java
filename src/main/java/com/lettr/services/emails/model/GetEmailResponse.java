package com.lettr.services.emails.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Response from retrieving details of a specific email.
 */
public class GetEmailResponse {

    private List<EmailEvent> results;

    @SerializedName("total_count")
    private int totalCount;

    /**
     * Returns all events for this email (injection, delivery, bounce, open, etc.).
     */
    public List<EmailEvent> getResults() {
        return results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return "GetEmailResponse{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
