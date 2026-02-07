package com.lettr.services.emails.model;

/**
 * Tracking options for an email.
 */
public class EmailOptions {

    private final Boolean click_tracking;
    private final Boolean open_tracking;
    private final Boolean transactional;

    private EmailOptions(Builder builder) {
        this.click_tracking = builder.clickTracking;
        this.open_tracking = builder.openTracking;
        this.transactional = builder.transactional;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Boolean getClickTracking() {
        return click_tracking;
    }

    public Boolean getOpenTracking() {
        return open_tracking;
    }

    public Boolean getTransactional() {
        return transactional;
    }

    public static class Builder {
        private Boolean clickTracking;
        private Boolean openTracking;
        private Boolean transactional;

        private Builder() {}

        /**
         * Enable or disable click tracking.
         */
        public Builder clickTracking(boolean clickTracking) {
            this.clickTracking = clickTracking;
            return this;
        }

        /**
         * Enable or disable open tracking.
         */
        public Builder openTracking(boolean openTracking) {
            this.openTracking = openTracking;
            return this;
        }

        /**
         * Mark the email as transactional or non-transactional.
         */
        public Builder transactional(boolean transactional) {
            this.transactional = transactional;
            return this;
        }

        public EmailOptions build() {
            return new EmailOptions(this);
        }
    }
}
