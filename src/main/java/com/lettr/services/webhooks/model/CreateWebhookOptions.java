package com.lettr.services.webhooks.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Options for creating a new webhook.
 */
public class CreateWebhookOptions {

    private final String name;
    private final String url;

    @SerializedName("auth_type")          private final String authType;
    @SerializedName("auth_username")      private final String authUsername;
    @SerializedName("auth_password")      private final String authPassword;
    @SerializedName("oauth_client_id")    private final String oauthClientId;
    @SerializedName("oauth_client_secret") private final String oauthClientSecret;
    @SerializedName("oauth_token_url")    private final String oauthTokenUrl;
    @SerializedName("events_mode")        private final String eventsMode;

    private final List<String> events;

    private CreateWebhookOptions(Builder builder) {
        this.name = builder.name;
        this.url = builder.url;
        this.authType = builder.authType;
        this.authUsername = builder.authUsername;
        this.authPassword = builder.authPassword;
        this.oauthClientId = builder.oauthClientId;
        this.oauthClientSecret = builder.oauthClientSecret;
        this.oauthTokenUrl = builder.oauthTokenUrl;
        this.eventsMode = builder.eventsMode;
        this.events = builder.events;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getName() { return name; }
    @Nonnull public String getUrl() { return url; }
    @Nonnull public String getAuthType() { return authType; }
    @Nullable public String getAuthUsername() { return authUsername; }
    @Nullable public String getAuthPassword() { return authPassword; }
    @Nullable public String getOauthClientId() { return oauthClientId; }
    @Nullable public String getOauthClientSecret() { return oauthClientSecret; }
    @Nullable public String getOauthTokenUrl() { return oauthTokenUrl; }
    @Nonnull public String getEventsMode() { return eventsMode; }
    @Nullable public List<String> getEvents() { return events; }

    public static class Builder {
        private String name;
        private String url;
        private String authType;
        private String authUsername;
        private String authPassword;
        private String oauthClientId;
        private String oauthClientSecret;
        private String oauthTokenUrl;
        private String eventsMode;
        private List<String> events;

        private Builder() {}

        /** <b>(required)</b> Sets the webhook name. Max length: 255. */
        @Nonnull public Builder name(@Nonnull String name) { this.name = name; return this; }

        /** <b>(required)</b> Sets the URL where webhook events will be sent. Max length: 2048. */
        @Nonnull public Builder url(@Nonnull String url) { this.url = url; return this; }

        /** <b>(required)</b> Sets the authentication type: {@code none}, {@code basic}, or {@code oauth2}. */
        @Nonnull public Builder authType(@Nonnull String authType) { this.authType = authType; return this; }

        /** <b>(optional, required when authType is "basic")</b> Sets the basic-auth username. */
        @Nonnull public Builder authUsername(@Nullable String authUsername) { this.authUsername = authUsername; return this; }

        /** <b>(optional, required when authType is "basic")</b> Sets the basic-auth password. */
        @Nonnull public Builder authPassword(@Nullable String authPassword) { this.authPassword = authPassword; return this; }

        /** <b>(optional, required when authType is "oauth2")</b> Sets the OAuth2 client ID. */
        @Nonnull public Builder oauthClientId(@Nullable String oauthClientId) { this.oauthClientId = oauthClientId; return this; }

        /** <b>(optional, required when authType is "oauth2")</b> Sets the OAuth2 client secret. */
        @Nonnull public Builder oauthClientSecret(@Nullable String oauthClientSecret) { this.oauthClientSecret = oauthClientSecret; return this; }

        /** <b>(optional, required when authType is "oauth2")</b> Sets the OAuth2 token URL. */
        @Nonnull public Builder oauthTokenUrl(@Nullable String oauthTokenUrl) { this.oauthTokenUrl = oauthTokenUrl; return this; }

        /** <b>(required)</b> Sets the events mode: {@code all} or {@code selected}. */
        @Nonnull public Builder eventsMode(@Nonnull String eventsMode) { this.eventsMode = eventsMode; return this; }

        /** <b>(optional, required when eventsMode is "selected")</b> Sets the event types to receive. */
        @Nonnull public Builder events(@Nullable List<String> events) { this.events = events; return this; }

        /**
         * @throws IllegalArgumentException if {@code name}, {@code url}, {@code authType}, or {@code eventsMode} is missing
         */
        @Nonnull
        public CreateWebhookOptions build() {
            if (name == null || name.isEmpty()) throw new IllegalArgumentException("'name' is required");
            if (url == null || url.isEmpty()) throw new IllegalArgumentException("'url' is required");
            if (authType == null || authType.isEmpty()) throw new IllegalArgumentException("'authType' is required");
            if (eventsMode == null || eventsMode.isEmpty()) throw new IllegalArgumentException("'eventsMode' is required");
            return new CreateWebhookOptions(this);
        }
    }
}
