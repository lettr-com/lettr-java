package com.lettr.services.audience.segments.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UpdateAudienceSegmentOptions {

    private final String name;

    @SerializedName("list_id")
    private final String listId;

    private final SegmentConditionsInput conditions;

    private UpdateAudienceSegmentOptions(Builder builder) {
        this.name = builder.name;
        this.listId = builder.listId;
        this.conditions = builder.conditions;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable public String getName() { return name; }
    @Nullable public String getListId() { return listId; }
    @Nullable public SegmentConditionsInput getConditions() { return conditions; }

    public static class Builder {
        private String name;
        private String listId;
        private SegmentConditionsInput conditions;

        private Builder() {}

        @Nonnull
        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @Nonnull
        public Builder listId(@Nullable String listId) {
            this.listId = listId;
            return this;
        }

        @Nonnull
        public Builder conditions(@Nullable SegmentConditionsInput conditions) {
            this.conditions = conditions;
            return this;
        }

        @Nonnull
        public UpdateAudienceSegmentOptions build() {
            return new UpdateAudienceSegmentOptions(this);
        }
    }
}
