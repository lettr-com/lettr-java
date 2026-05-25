package com.lettr.services.audience.topics.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request body for partially updating an audience topic. Note that
 * {@code defaultSubscription} cannot be changed after creation and is
 * intentionally not exposed here.
 */
public class UpdateAudienceTopicOptions {

    private final String name;
    private final String description;
    private final AudienceTopicVisibility visibility;

    private UpdateAudienceTopicOptions(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.visibility = builder.visibility;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable public String getName() { return name; }
    @Nullable public String getDescription() { return description; }
    @Nullable public AudienceTopicVisibility getVisibility() { return visibility; }

    public static class Builder {
        private String name;
        private String description;
        private AudienceTopicVisibility visibility;

        private Builder() {}

        @Nonnull
        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @Nonnull
        public Builder description(@Nullable String description) {
            this.description = description;
            return this;
        }

        @Nonnull
        public Builder visibility(@Nullable AudienceTopicVisibility visibility) {
            this.visibility = visibility;
            return this;
        }

        @Nonnull
        public UpdateAudienceTopicOptions build() {
            return new UpdateAudienceTopicOptions(this);
        }
    }
}
