package com.lettr.services.emails.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Tracking and delivery options for an email. All fields are optional;
 * omitted values fall back to the team's defaults.
 */
public class EmailOptions {

    private final Boolean click_tracking;
    private final Boolean open_tracking;
    private final Boolean transactional;
    private final Boolean inline_css;
    private final Boolean perform_substitutions;

    private EmailOptions(Builder builder) {
        this.click_tracking = builder.clickTracking;
        this.open_tracking = builder.openTracking;
        this.transactional = builder.transactional;
        this.inline_css = builder.inlineCss;
        this.perform_substitutions = builder.performSubstitutions;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable public Boolean getClickTracking() { return click_tracking; }
    @Nullable public Boolean getOpenTracking() { return open_tracking; }
    @Nullable public Boolean getTransactional() { return transactional; }
    @Nullable public Boolean getInlineCss() { return inline_css; }
    @Nullable public Boolean getPerformSubstitutions() { return perform_substitutions; }

    public static class Builder {
        private Boolean clickTracking;
        private Boolean openTracking;
        private Boolean transactional;
        private Boolean inlineCss;
        private Boolean performSubstitutions;

        private Builder() {}

        /** <b>(optional)</b> Enable or disable click tracking for links in the email. */
        @Nonnull
        public Builder clickTracking(boolean clickTracking) {
            this.clickTracking = clickTracking;
            return this;
        }

        /** <b>(optional)</b> Enable or disable open tracking via tracking pixel. */
        @Nonnull
        public Builder openTracking(boolean openTracking) {
            this.openTracking = openTracking;
            return this;
        }

        /** <b>(optional)</b> Mark the email as transactional or marketing. */
        @Nonnull
        public Builder transactional(boolean transactional) {
            this.transactional = transactional;
            return this;
        }

        /** <b>(optional)</b> Inline CSS styles in HTML content before sending. */
        @Nonnull
        public Builder inlineCss(boolean inlineCss) {
            this.inlineCss = inlineCss;
            return this;
        }

        /** <b>(optional)</b> Perform variable substitutions in content. */
        @Nonnull
        public Builder performSubstitutions(boolean performSubstitutions) {
            this.performSubstitutions = performSubstitutions;
            return this;
        }

        @Nonnull
        public EmailOptions build() {
            return new EmailOptions(this);
        }
    }
}
