package com.lettr.services.campaigns.model;

import com.lettr.core.model.OffsetPagination;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Response from listing campaigns: a page of campaigns plus pagination metadata.
 */
public class ListCampaignsResponse {

    private List<CampaignView> campaigns;
    private OffsetPagination pagination;

    @Nonnull
    public List<CampaignView> getCampaigns() {
        return campaigns != null ? campaigns : Collections.emptyList();
    }

    @Nonnull
    public OffsetPagination getPagination() {
        return pagination;
    }

    @Override
    public String toString() {
        return "ListCampaignsResponse{campaigns=" + campaigns + ", pagination=" + pagination + '}';
    }
}
