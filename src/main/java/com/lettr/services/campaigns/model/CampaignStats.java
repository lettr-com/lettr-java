package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

/**
 * Aggregated engagement statistics embedded in every campaign response.
 */
public class CampaignStats {

    private int injections;
    private int deliveries;
    private int bounces;

    @SerializedName("spam_complaints")
    private int spamComplaints;

    private int opens;

    @SerializedName("unique_opens")
    private int uniqueOpens;

    private int clicks;

    @SerializedName("unique_clicks")
    private int uniqueClicks;

    private int unsubscribes;

    public int getInjections() {
        return injections;
    }

    public int getDeliveries() {
        return deliveries;
    }

    public int getBounces() {
        return bounces;
    }

    public int getSpamComplaints() {
        return spamComplaints;
    }

    public int getOpens() {
        return opens;
    }

    public int getUniqueOpens() {
        return uniqueOpens;
    }

    public int getClicks() {
        return clicks;
    }

    public int getUniqueClicks() {
        return uniqueClicks;
    }

    public int getUnsubscribes() {
        return unsubscribes;
    }

    @Override
    public String toString() {
        return "CampaignStats{injections=" + injections
                + ", deliveries=" + deliveries
                + ", bounces=" + bounces
                + ", spamComplaints=" + spamComplaints
                + ", opens=" + opens
                + ", uniqueOpens=" + uniqueOpens
                + ", clicks=" + clicks
                + ", uniqueClicks=" + uniqueClicks
                + ", unsubscribes=" + unsubscribes + '}';
    }
}
