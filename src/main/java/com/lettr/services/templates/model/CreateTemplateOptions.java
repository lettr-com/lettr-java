package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Options for creating a new email template.
 *
 * <p>Either {@code html} or {@code json} content must be provided, but not both.</p>
 */
public class CreateTemplateOptions {

    private final String name;
    private final String html;
    private final String json;

    @SerializedName("project_id") private final Integer projectId;
    @SerializedName("folder_id")  private final Integer folderId;

    private CreateTemplateOptions(Builder builder) {
        this.name = builder.name;
        this.html = builder.html;
        this.json = builder.json;
        this.projectId = builder.projectId;
        this.folderId = builder.folderId;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getName() { return name; }
    @Nullable public String getHtml() { return html; }
    @Nullable public String getJson() { return json; }
    @Nullable public Integer getProjectId() { return projectId; }
    @Nullable public Integer getFolderId() { return folderId; }

    public static class Builder {
        private String name;
        private String html;
        private String json;
        private Integer projectId;
        private Integer folderId;

        private Builder() {}

        /** <b>(required)</b> Sets the template name. Max length: 255. */
        @Nonnull public Builder name(@Nonnull String name) { this.name = name; return this; }

        /**
         * <b>(required if json not provided)</b> Sets the HTML content for custom HTML templates.
         * Mutually exclusive with {@link #json(String)}.
         */
        @Nonnull public Builder html(@Nullable String html) { this.html = html; return this; }

        /**
         * <b>(required if html not provided)</b> Sets the Topol JSON content for visual editor templates.
         * Mutually exclusive with {@link #html(String)}.
         */
        @Nonnull public Builder json(@Nullable String json) { this.json = json; return this; }

        /** <b>(optional)</b> Sets the project ID. Defaults to the team's default project. */
        @Nonnull public Builder projectId(int projectId) { this.projectId = projectId; return this; }

        /** <b>(optional)</b> Sets the folder ID. Defaults to the first folder in the project. */
        @Nonnull public Builder folderId(int folderId) { this.folderId = folderId; return this; }

        /**
         * @throws IllegalArgumentException if {@code name} is missing, neither {@code html} nor
         *         {@code json} is provided, or both are provided
         */
        @Nonnull
        public CreateTemplateOptions build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("'name' is required");
            }
            if (html == null && json == null) {
                throw new IllegalArgumentException("Either 'html' or 'json' is required");
            }
            if (html != null && json != null) {
                throw new IllegalArgumentException("'html' and 'json' are mutually exclusive");
            }
            return new CreateTemplateOptions(this);
        }
    }
}
