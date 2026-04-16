package com.lettr.services.projects.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a project in the Lettr system.
 */
public class Project {

    private int id;
    private String name;
    private String emoji;

    @SerializedName("team_id")    private int teamId;
    @SerializedName("created_at") private String createdAt;
    @SerializedName("updated_at") private String updatedAt;

    public int getId() { return id; }
    @Nonnull public String getName() { return name; }
    @Nullable public String getEmoji() { return emoji; }
    public int getTeamId() { return teamId; }
    @Nonnull public String getCreatedAt() { return createdAt; }
    @Nonnull public String getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "Project{id=" + id + ", name='" + name + "'}";
    }
}
