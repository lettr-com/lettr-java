package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

/**
 * Options for creating a new email template.
 *
 * <p>Either {@code html} or {@code json} content must be provided, but not both.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CreateTemplateOptions options = CreateTemplateOptions.builder()
 *     .name("Welcome Email")
 *     .html("<p>Hello {{FIRST_NAME}}!</p>")
 *     .build();
 * }</pre>
 */
public class CreateTemplateOptions {

    private final String name;
    private final String html;
    private final String json;

    @SerializedName("project_id")
    private final Integer projectId;

    @SerializedName("folder_id")
    private final Integer folderId;

    private CreateTemplateOptions(Builder builder) {
        this.name = builder.name;
        this.html = builder.html;
        this.json = builder.json;
        this.projectId = builder.projectId;
        this.folderId = builder.folderId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() { return name; }
    public String getHtml() { return html; }
    public String getJson() { return json; }
    public Integer getProjectId() { return projectId; }
    public Integer getFolderId() { return folderId; }

    public static class Builder {
        private String name;
        private String html;
        private String json;
        private Integer projectId;
        private Integer folderId;

        private Builder() {}

        /**
         * Sets the template name (required).
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the HTML content for the template.
         * Mutually exclusive with {@link #json(String)}.
         */
        public Builder html(String html) {
            this.html = html;
            return this;
        }

        /**
         * Sets the Topol editor JSON content for the template.
         * Mutually exclusive with {@link #html(String)}.
         */
        public Builder json(String json) {
            this.json = json;
            return this;
        }

        /**
         * Sets the project ID to create the template in.
         * If not specified, uses the team's default project.
         */
        public Builder projectId(int projectId) {
            this.projectId = projectId;
            return this;
        }

        /**
         * Sets the folder ID within the project.
         */
        public Builder folderId(int folderId) {
            this.folderId = folderId;
            return this;
        }

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
