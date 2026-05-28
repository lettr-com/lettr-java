package com.lettr.services.campaigns.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Response from listing campaign engagement events. Uses cursor-based pagination:
 * keep requesting with {@link #getNextCursor()} until it is {@code null}.
 *
 * <p>When a filter is applied, a page may come back with an empty {@code events}
 * list <b>and</b> a non-null {@code nextCursor} — that means more pages remain,
 * not that there are no matching events, so continue paginating until
 * {@code nextCursor} is {@code null}.</p>
 */
public class ListCampaignEventsResponse {

    private List<CampaignEvent> events;

    @SerializedName("next_cursor")
    private String nextCursor;

    @Nonnull
    public List<CampaignEvent> getEvents() {
        return events != null ? events : Collections.emptyList();
    }

    /** Cursor for the next page, or {@code null} if there are no more results. */
    @Nullable
    public String getNextCursor() {
        return nextCursor;
    }

    @Override
    public String toString() {
        return "ListCampaignEventsResponse{events=" + events + ", nextCursor='" + nextCursor + "'}";
    }
}
