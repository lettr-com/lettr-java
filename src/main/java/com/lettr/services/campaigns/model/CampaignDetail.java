package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

/**
 * Detailed view of a campaign returned by
 * {@link com.lettr.services.campaigns.Campaigns#get(String)}, adding the
 * rendered {@code htmlContent} on top of {@link CampaignView}.
 */
public class CampaignDetail extends CampaignView {

    @SerializedName("html_content")
    private String htmlContent;

    /** Rendered HTML content of the campaign. May be {@code null} for drafts with no content yet. */
    @Nullable
    public String getHtmlContent() {
        return htmlContent;
    }

    @Override
    public String toString() {
        return "CampaignDetail{id='" + getId() + "', name='" + getName()
                + "', status=" + getStatus() + ", sentCount=" + getSentCount()
                + ", hasHtmlContent=" + (htmlContent != null) + '}';
    }
}
