package com.lettr.services.templates.model;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parameters for getting template HTML content. Both {@code projectId} and {@code slug} are required.
 */
public class GetTemplateHtmlParams {

    private final int projectId;
    private final String slug;

    private GetTemplateHtmlParams(Builder builder) {
        this.projectId = builder.projectId;
        this.slug = builder.slug;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("project_id", Integer.toString(projectId));
        params.put("slug", slug);
        return params;
    }

    public static class Builder {
        private int projectId;
        private String slug;

        private Builder() {}

        /** <b>(required)</b> Sets the project ID containing the template. */
        @Nonnull public Builder projectId(int projectId) { this.projectId = projectId; return this; }

        /** <b>(required)</b> Sets the template slug. */
        @Nonnull public Builder slug(@Nonnull String slug) { this.slug = slug; return this; }

        /**
         * @throws IllegalArgumentException if {@code slug} is missing
         */
        @Nonnull
        public GetTemplateHtmlParams build() {
            if (slug == null || slug.isEmpty()) {
                throw new IllegalArgumentException("'slug' is required");
            }
            return new GetTemplateHtmlParams(this);
        }
    }
}
