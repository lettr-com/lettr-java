package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Detailed view of an email template including content.
 */
public class TemplateDetail {

    private int id;
    private String name;
    private String slug;

    @SerializedName("project_id") private int projectId;
    @SerializedName("folder_id")  private Integer folderId;

    @SerializedName("active_version")
    private Integer activeVersion;

    @SerializedName("versions_count")
    private int versionsCount;

    private String html;
    private String json;

    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;

    public int getId() { return id; }
    @Nonnull public String getName() { return name; }
    @Nonnull public String getSlug() { return slug; }
    public int getProjectId() { return projectId; }
    @Nonnull public Integer getFolderId() { return folderId; }
    /** Active version number, or null if no version is active. */
    @Nullable public Integer getActiveVersion() { return activeVersion; }
    public int getVersionsCount() { return versionsCount; }
    /** HTML content of the active version, or null if no active version exists. */
    @Nullable public String getHtml() { return html; }
    /** Topol JSON of the active version, or null for custom HTML templates / no active version. */
    @Nullable public String getJson() { return json; }
    @Nonnull public String getCreatedAt() { return createdAt; }
    @Nonnull public String getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "TemplateDetail{id=" + id + ", slug='" + slug + "', activeVersion=" + activeVersion + '}';
    }
}
