package com.lettr.services.audience.segments.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CreateAudienceSegmentOptions {

    private final String name;

    @SerializedName("list_id")
    private final String listId;

    private final SegmentConditionsInput conditions;

    private CreateAudienceSegmentOptions(Builder builder) {
        this.name = builder.name;
        this.listId = builder.listId;
        this.conditions = builder.conditions;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getName() { return name; }
    @Nullable public String getListId() { return listId; }
    @Nonnull public SegmentConditionsInput getConditions() { return conditions; }

    public static class Builder {
        private String name;
        private String listId;
        private SegmentConditionsInput conditions;

        private Builder() {}

        /** <b>(required)</b> Segment name (max 255). */
        @Nonnull
        public Builder name(@Nonnull String name) {
            this.name = name;
            return this;
        }

        /** <b>(optional)</b> Restrict the segment to contacts within this list. */
        @Nonnull
        public Builder listId(@Nullable String listId) {
            this.listId = listId;
            return this;
        }

        /** <b>(required)</b> Condition groups defining the segment. */
        @Nonnull
        public Builder conditions(@Nonnull SegmentConditionsInput conditions) {
            this.conditions = conditions;
            return this;
        }

        @Nonnull
        public CreateAudienceSegmentOptions build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name is required");
            }
            if (conditions == null) {
                throw new IllegalArgumentException("conditions is required");
            }
            return new CreateAudienceSegmentOptions(this);
        }
    }
}
