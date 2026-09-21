package com.lettr.services.emails.model;

import com.lettr.core.model.PageParams;
import com.lettr.core.util.WireValues;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parameters for listing scheduled emails. All fields are optional. Composes
 * {@link PageParams} for {@code page} / {@code per_page} and adds a
 * {@code status} filter.
 */
public class ListScheduledEmailsParams {

    private final PageParams pageParams;
    private final ScheduledEmailState status;

    private ListScheduledEmailsParams(Builder builder) {
        this.pageParams = builder.pageParams.build();
        this.status = builder.status;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>(pageParams.toQueryParams());
        if (status != null) params.put("status", WireValues.of(status));
        return params;
    }

    public static class Builder {
        private final PageParams.Builder pageParams = PageParams.builder();
        private ScheduledEmailState status;

        private Builder() {}

        /** <b>(optional)</b> Page number (min 1, default 1). */
        @Nonnull
        public Builder page(@Nullable Integer page) {
            pageParams.page(page);
            return this;
        }

        /** <b>(optional)</b> Items per page (1–100, default 25). */
        @Nonnull
        public Builder perPage(@Nullable Integer perPage) {
            pageParams.perPage(perPage);
            return this;
        }

        /** <b>(optional)</b> Filters by lifecycle state. */
        @Nonnull
        public Builder status(@Nullable ScheduledEmailState status) {
            this.status = status;
            return this;
        }

        @Nonnull
        public ListScheduledEmailsParams build() {
            return new ListScheduledEmailsParams(this);
        }
    }
}
