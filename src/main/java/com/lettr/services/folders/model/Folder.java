package com.lettr.services.folders.model;

import com.google.gson.annotations.SerializedName;
import com.lettr.services.templates.model.TemplatePurpose;

import javax.annotation.Nonnull;

/**
 * A folder templates are filed into.
 *
 * <p>{@link #getId()} is what {@code CreateTemplateOptions.folderId()} expects,
 * so listing folders is how a caller picks where a template lands instead of
 * hardcoding an integer read out of an app URL.
 */
public class Folder {

    private int id;
    private String name;

    @SerializedName("project_id")      private int projectId;
    private TemplatePurpose purpose;
    @SerializedName("templates_count") private int templatesCount;
    @SerializedName("created_at")      private String createdAt;
    @SerializedName("updated_at")      private String updatedAt;

    public int getId() { return id; }
    @Nonnull public String getName() { return name; }
    public int getProjectId() { return projectId; }

    /**
     * The module this folder belongs to. A template can only be filed into a
     * folder of its own module.
     */
    @Nonnull
    public TemplatePurpose getPurpose() {
        return purpose != null ? purpose : TemplatePurpose.TRANSACTIONAL;
    }

    /** How many templates are in this folder. */
    public int getTemplatesCount() { return templatesCount; }

    @Nonnull public String getCreatedAt() { return createdAt; }
    @Nonnull public String getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "Folder{id=" + id + ", name='" + name + "', purpose=" + getPurpose() + '}';
    }
}
