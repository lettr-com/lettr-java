package com.lettr.services.webhooks.model;

import com.google.gson.annotations.SerializedName;
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

    public String getId() { return id; }
    public String getName() { return name; }
    public String getUrl() { return url; }
    public boolean isEnabled() { return enabled; }
    public List<String> getEventTypes() { return eventTypes; }
    public String getAuthType() { return authType; }
    public boolean isHasAuthCredentials() { return hasAuthCredentials; }
    public String getLastSuccessfulAt() { return lastSuccessfulAt; }
    public String getLastFailureAt() { return lastFailureAt; }
    public String getLastStatus() { return lastStatus; }

    @Override
    public String toString() {
        return "Webhook{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
