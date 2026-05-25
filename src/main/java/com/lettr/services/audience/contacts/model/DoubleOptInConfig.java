package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Configuration for sending a double opt-in confirmation email when creating a contact.
 */
public class DoubleOptInConfig {

    private final String from;

    @SerializedName("from_name")
    private final String fromName;

    private final String subject;

    @SerializedName("template_slug")
    private final String templateSlug;

    @SerializedName("redirect_url")
    private final String redirectUrl;

    private DoubleOptInConfig(Builder builder) {
        this.from = builder.from;
        this.fromName = builder.fromName;
        this.subject = builder.subject;
        this.templateSlug = builder.templateSlug;
        this.redirectUrl = builder.redirectUrl;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getFrom() { return from; }
    @Nullable public String getFromName() { return fromName; }
    @Nonnull public String getSubject() { return subject; }
    @Nonnull public String getTemplateSlug() { return templateSlug; }
    @Nonnull public String getRedirectUrl() { return redirectUrl; }

    public static class Builder {
        private String from;
        private String fromName;
        private String subject;
        private String templateSlug;
        private String redirectUrl;

        private Builder() {}

        /** <b>(required)</b> The sender email address. */
        @Nonnull
        public Builder from(@Nonnull String from) {
            this.from = from;
            return this;
        }

        /** <b>(optional)</b> The sender's display name. */
        @Nonnull
        public Builder fromName(@Nullable String fromName) {
            this.fromName = fromName;
            return this;
        }

        /** <b>(required)</b> The subject line for the confirmation email. */
        @Nonnull
        public Builder subject(@Nonnull String subject) {
            this.subject = subject;
            return this;
        }

        /** <b>(required)</b> The slug of the email template to use. */
        @Nonnull
        public Builder templateSlug(@Nonnull String templateSlug) {
            this.templateSlug = templateSlug;
            return this;
        }

        /** <b>(required)</b> Redirect URL after the contact confirms. */
        @Nonnull
        public Builder redirectUrl(@Nonnull String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        @Nonnull
        public DoubleOptInConfig build() {
            if (from == null || from.isEmpty()) {
                throw new IllegalArgumentException("from is required");
            }
            if (subject == null || subject.isEmpty()) {
                throw new IllegalArgumentException("subject is required");
            }
            if (templateSlug == null || templateSlug.isEmpty()) {
                throw new IllegalArgumentException("templateSlug is required");
            }
            if (redirectUrl == null || redirectUrl.isEmpty()) {
                throw new IllegalArgumentException("redirectUrl is required");
            }
            return new DoubleOptInConfig(this);
        }
    }
}
