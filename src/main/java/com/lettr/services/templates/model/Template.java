package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents an email template.
 */
public class Template {

    private int id;
    private String name;
    private String slug;

    @SerializedName("project_id")
    private int projectId;

    @SerializedName("folder_id")
    private Integer folderId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public int getProjectId() { return projectId; }
    public Integer getFolderId() { return folderId; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
