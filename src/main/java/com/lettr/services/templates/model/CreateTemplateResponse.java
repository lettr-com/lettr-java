package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Response returned after creating a new template.
 */
public class CreateTemplateResponse {

    private int id;
    private String name;
    private String slug;

    @SerializedName("project_id")     private int projectId;
    @SerializedName("folder_id")      private Integer folderId;
    @SerializedName("active_version") private int activeVersion;
    @SerializedName("merge_tags")     private List<MergeTag> mergeTags;
    @SerializedName("created_at")     private String createdAt;

    public int getId() { return id; }
    @Nonnull public String getName() { return name; }
    @Nonnull public String getSlug() { return slug; }
    public int getProjectId() { return projectId; }
    @Nonnull public Integer getFolderId() { return folderId; }
    public int getActiveVersion() { return activeVersion; }
    @Nonnull public List<MergeTag> getMergeTags() { return mergeTags; }
    @Nonnull public String getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "CreateTemplateResponse{id=" + id + ", slug='" + slug + "'}";
    }
}
