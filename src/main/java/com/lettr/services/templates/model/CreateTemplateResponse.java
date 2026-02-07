package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Response returned after creating a new template.
 */
public class CreateTemplateResponse {

    private int id;
    private String name;
    private String slug;

    @SerializedName("project_id")
    private int projectId;

    @SerializedName("folder_id")
    private Integer folderId;

    @SerializedName("active_version")
    private int activeVersion;

    @SerializedName("merge_tags")
    private List<MergeTag> mergeTags;

    @SerializedName("created_at")
    private String createdAt;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public int getProjectId() { return projectId; }
    public Integer getFolderId() { return folderId; }
    public int getActiveVersion() { return activeVersion; }
    public List<MergeTag> getMergeTags() { return mergeTags; }
    public String getCreatedAt() { return createdAt; }

    /**
     * A merge tag extracted from the template content.
     */
    public static class MergeTag {
        private String key;
        private boolean required;

        public String getKey() { return key; }
        public boolean isRequired() { return required; }

        @Override
        public String toString() {
            return "MergeTag{key='" + key + "', required=" + required + '}';
        }
    }

    @Override
    public String toString() {
        return "CreateTemplateResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }
}
