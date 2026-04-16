package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Response containing merge tags for a template version.
 */
public class GetMergeTagsResponse {

    @SerializedName("project_id")    private int projectId;
    @SerializedName("template_slug") private String templateSlug;

    private int version;

    @SerializedName("merge_tags")
    private List<MergeTag> mergeTags;

    public int getProjectId() { return projectId; }
    @Nonnull public String getTemplateSlug() { return templateSlug; }
    public int getVersion() { return version; }
    @Nonnull public List<MergeTag> getMergeTags() { return mergeTags; }

    @Override
    public String toString() {
        return "GetMergeTagsResponse{templateSlug='" + templateSlug + "', version=" + version + '}';
    }
}
