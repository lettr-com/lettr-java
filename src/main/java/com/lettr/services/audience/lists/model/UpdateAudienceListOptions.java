package com.lettr.services.audience.lists.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Request body for partially updating an audience list. All fields optional.
 */
public class UpdateAudienceListOptions {

    private final String name;

    private UpdateAudienceListOptions(Builder builder) {
        this.name = builder.name;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable
    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;

        private Builder() {}

        /** <b>(optional)</b> New list name (max 255). */
        @Nonnull
        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @Nonnull
        public UpdateAudienceListOptions build() {
            return new UpdateAudienceListOptions(this);
        }
    }
}
