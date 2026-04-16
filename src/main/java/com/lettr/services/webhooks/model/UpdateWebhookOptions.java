package com.lettr.services.webhooks.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Options for updating an existing webhook. All fields are optional;
 * only provided fields will be updated.
 */
public class UpdateWebhookOptions {

    private final String name;
    private final String target;

    @SerializedName("auth_type")           private final String authType;
    @SerializedName("auth_username")       private final String authUsername;
    @SerializedName("auth_password")       private final String authPassword;
    @SerializedName("oauth_token_url")     private final String oauthTokenUrl;
    @SerializedName("oauth_client_id")     private final String oauthClientId;
    @SerializedName("oauth_client_secret") private final String oauthClientSecret;

    private final List<String> events;
    private final Boolean active;

    private UpdateWebhookOptions(Builder builder) {
        this.name = builder.name;
        this.target = builder.target;
        this.authType = builder.authType;
        this.authUsername = builder.authUsername;
        this.authPassword = builder.authPassword;
        this.oauthTokenUrl = builder.oauthTokenUrl;
        this.oauthClientId = builder.oauthClientId;
        this.oauthClientSecret = builder.oauthClientSecret;
        this.events = builder.events;
        this.active = builder.active;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable public String getName() { return name; }
    @Nullable public String getTarget() { return target; }
    @Nullable public String getAuthType() { return authType; }
    @Nullable public String getAuthUsername() { return authUsername; }
    @Nullable public String getAuthPassword() { return authPassword; }
    @Nullable public String getOauthTokenUrl() { return oauthTokenUrl; }
    @Nullable public String getOauthClientId() { return oauthClientId; }
    @Nullable public String getOauthClientSecret() { return oauthClientSecret; }
    @Nullable public List<String> getEvents() { return events; }
    @Nullable public Boolean getActive() { return active; }

    public static class Builder {
        private String name;
        private String target;
        private String authType;
        private String authUsername;
        private String authPassword;
        private String oauthTokenUrl;
        private String oauthClientId;
        private String oauthClientSecret;
        private List<String> events;
        private Boolean active;

        private Builder() {}

        /** <b>(optional)</b> Sets the new webhook name. Max length: 255. */
        @Nonnull public Builder name(@Nullable String name) { this.name = name; return this; }

        /** <b>(optional)</b> Sets the new webhook target URL. Max length: 2048. */
        @Nonnull public Builder target(@Nullable String target) { this.target = target; return this; }

        /** <b>(optional)</b> Sets the new authentication type. */
        @Nonnull public Builder authType(@Nullable String authType) { this.authType = authType; return this; }

        /** <b>(optional)</b> Sets the new basic-auth username. */
        @Nonnull public Builder authUsername(@Nullable String authUsername) { this.authUsername = authUsername; return this; }

        /** <b>(optional)</b> Sets the new basic-auth password. */
        @Nonnull public Builder authPassword(@Nullable String authPassword) { this.authPassword = authPassword; return this; }

        /** <b>(optional)</b> Sets the new OAuth2 token URL. */
        @Nonnull public Builder oauthTokenUrl(@Nullable String oauthTokenUrl) { this.oauthTokenUrl = oauthTokenUrl; return this; }

        /** <b>(optional)</b> Sets the new OAuth2 client ID. */
        @Nonnull public Builder oauthClientId(@Nullable String oauthClientId) { this.oauthClientId = oauthClientId; return this; }

        /** <b>(optional)</b> Sets the new OAuth2 client secret. */
        @Nonnull public Builder oauthClientSecret(@Nullable String oauthClientSecret) { this.oauthClientSecret = oauthClientSecret; return this; }

        /** <b>(optional)</b> Sets the events that trigger the webhook. */
        @Nonnull public Builder events(@Nullable List<String> events) { this.events = events; return this; }

        /** <b>(optional)</b> Enables or disables the webhook. */
        @Nonnull public Builder active(boolean active) { this.active = active; return this; }

        @Nonnull
        public UpdateWebhookOptions build() {
            return new UpdateWebhookOptions(this);
        }
    }
}
