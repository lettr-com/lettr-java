package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options for updating an existing template. All fields are optional;
 * {@code html} and {@code json} are mutually exclusive.
 */
public class UpdateTemplateOptions {

    @SerializedName("project_id") private final Integer projectId;
    private final String name;
    private final String html;
    private final String json;

    private UpdateTemplateOptions(Builder builder) {
        this.projectId = builder.projectId;
        this.name = builder.name;
        this.html = builder.html;
        this.json = builder.json;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable public Integer getProjectId() { return projectId; }
    @Nullable public String getName() { return name; }
    @Nullable public String getHtml() { return html; }
    @Nullable public String getJson() { return json; }

    public static class Builder {
        private Integer projectId;
        private String name;
        private String html;
        private String json;

        private Builder() {}

        /** <b>(optional)</b> Sets the project ID. Defaults to the team's default project. */
        @Nonnull public Builder projectId(int projectId) { this.projectId = projectId; return this; }

        /** <b>(optional)</b> Sets the new template name. Max length: 255. */
        @Nonnull public Builder name(@Nullable String name) { this.name = name; return this; }

        /** <b>(optional)</b> Sets the new HTML content. Creates a new active version. Mutually exclusive with {@link #json(String)}. */
        @Nonnull public Builder html(@Nullable String html) { this.html = html; return this; }

        /** <b>(optional)</b> Sets the new Topol JSON content. Creates a new active version. Mutually exclusive with {@link #html(String)}. */
        @Nonnull public Builder json(@Nullable String json) { this.json = json; return this; }

        /**
         * @throws IllegalArgumentException if both {@code html} and {@code json} are provided
         */
        @Nonnull
        public UpdateTemplateOptions build() {
            if (html != null && json != null) {
                throw new IllegalArgumentException("'html' and 'json' are mutually exclusive");
            }
            return new UpdateTemplateOptions(this);
        }
    }
}
