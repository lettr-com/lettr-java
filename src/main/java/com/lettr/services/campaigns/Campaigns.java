package com.lettr.services.campaigns;

import com.lettr.core.exception.LettrException;
import com.lettr.core.net.HttpClient;
import com.lettr.core.util.Args;
import com.lettr.services.BaseService;
import com.lettr.services.campaigns.model.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for listing, inspecting, sending, and scheduling campaigns via the
 * Lettr API. Read operations require the {@code campaigns:read} scope; send,
 * schedule, and unschedule require {@code campaigns:write} and are not available
 * to sandbox keys.
 */
public class Campaigns extends BaseService {

    public Campaigns(@Nonnull String apiKey) {
        super(apiKey);
    }

    /**
     * List campaigns with optional filtering and pagination.
     *
     * @param params optional query parameters; pass null for defaults
     * @return a page of campaigns with pagination metadata
     * @throws LettrException if the request fails
     */
    @Nonnull
    public ListCampaignsResponse list(@Nullable ListCampaignsParams params) throws LettrException {
        return httpClient.get("/campaigns", params != null ? params.toQueryParams() : null, ListCampaignsResponse.class);
    }

    /** List campaigns with default pagination. */
    @Nonnull
    public ListCampaignsResponse list() throws LettrException {
        return list(null);
    }

    /**
     * Retrieve a single campaign. The returned {@link CampaignDetail} exposes
     * the rendered HTML via {@link CampaignDetail#getHtmlContent()}, which may
     * be {@code null} for drafts that have no rendered content yet.
     *
     * @param campaignId the campaign ID
     * @return the campaign detail; {@code getHtmlContent()} may be {@code null} for drafts
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code campaignId} is null or empty
     */
    @Nonnull
    public CampaignDetail get(@Nonnull String campaignId) throws LettrException {
        Args.requireNonEmpty("campaignId", campaignId);
        return httpClient.get("/campaigns/" + HttpClient.encodePathSegment(campaignId), null, CampaignDetail.class);
    }

    /**
     * List engagement events for a campaign with optional filtering. Uses
     * cursor-based pagination via {@link ListCampaignEventsResponse#getNextCursor()}.
     *
     * @param campaignId the campaign ID
     * @param params     optional query parameters; pass null for defaults
     * @return a page of events with a next-page cursor
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code campaignId} is null or empty
     */
    @Nonnull
    public ListCampaignEventsResponse listEvents(@Nonnull String campaignId, @Nullable ListCampaignEventsParams params) throws LettrException {
        Args.requireNonEmpty("campaignId", campaignId);
        return httpClient.get("/campaigns/" + HttpClient.encodePathSegment(campaignId) + "/events",
                params != null ? params.toQueryParams() : null,
                ListCampaignEventsResponse.class);
    }

    /** List campaign engagement events with default parameters. */
    @Nonnull
    public ListCampaignEventsResponse listEvents(@Nonnull String campaignId) throws LettrException {
        return listEvents(campaignId, null);
    }

    /**
     * Immediately send a draft campaign. Sending is asynchronous; the campaign
     * transitions to {@code preparing}.
     *
     * @param campaignId the campaign ID
     * @return the updated campaign
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code campaignId} is null or empty
     */
    @Nonnull
    public CampaignView send(@Nonnull String campaignId) throws LettrException {
        Args.requireNonEmpty("campaignId", campaignId);
        return httpClient.post("/campaigns/" + HttpClient.encodePathSegment(campaignId) + "/send", CampaignView.class);
    }

    /**
     * Schedule a campaign for future delivery, or reschedule an already-scheduled
     * campaign to a new time.
     *
     * @param campaignId the campaign ID
     * @param options    schedule options including {@code scheduledAt}
     * @return the updated campaign
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code campaignId} is null/empty or {@code options} is null
     */
    @Nonnull
    public CampaignView schedule(@Nonnull String campaignId, @Nonnull ScheduleCampaignOptions options) throws LettrException {
        Args.requireNonEmpty("campaignId", campaignId);
        Args.requireNonNull("options", options);
        return httpClient.post("/campaigns/" + HttpClient.encodePathSegment(campaignId) + "/schedule", options, CampaignView.class);
    }

    /**
     * Cancel a scheduled send, returning the campaign to {@code draft}.
     *
     * @param campaignId the campaign ID
     * @return the updated campaign
     * @throws LettrException if the request fails
     * @throws IllegalArgumentException if {@code campaignId} is null or empty
     */
    @Nonnull
    public CampaignView unschedule(@Nonnull String campaignId) throws LettrException {
        Args.requireNonEmpty("campaignId", campaignId);
        return httpClient.post("/campaigns/" + HttpClient.encodePathSegment(campaignId) + "/unschedule", CampaignView.class);
    }
}
