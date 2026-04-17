package com.lettr.services.webhooks.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents a webhook configuration.
 */
public class Webhook {

    private String id;
    private String name;
    private String url;
    private boolean enabled;

    @SerializedName("event_types")
    private List<String> eventTypes;

    @SerializedName("auth_type")
    private String authType;

    @SerializedName("has_auth_credentials")
    private boolean hasAuthCredentials;

    @SerializedName("last_successful_at")
    private String lastSuccessfulAt;

    @SerializedName("last_failure_at")
    private String lastFailureAt;

    @SerializedName("last_status")
    private String lastStatus;

    @Nonnull public String getId() { return id; }
    @Nonnull public String getName() { return name; }
    @Nonnull public String getUrl() { return url; }
    public boolean isEnabled() { return enabled; }
    /** Specific event types the webhook receives, or null if subscribed to all events. */
    @Nullable public List<String> getEventTypes() { return eventTypes; }
    /** {@code none}, {@code basic}, or {@code oauth2}. */
    @Nonnull public String getAuthType() { return authType; }
    public boolean isHasAuthCredentials() { return hasAuthCredentials; }
    /** Timestamp of the last successful webhook delivery, or null if there has been none. */
    @Nullable public String getLastSuccessfulAt() { return lastSuccessfulAt; }
    /** Timestamp of the last failed webhook delivery, or null if there has been none. */
    @Nullable public String getLastFailureAt() { return lastFailureAt; }
    /** {@code success}, {@code failure}, or null if there has been no delivery yet. */
    @Nullable public String getLastStatus() { return lastStatus; }

    @Override
    public String toString() {
        return "Webhook{id='" + id + "', name='" + name + "', enabled=" + enabled + '}';
    }
}
