package com.lettr.services.audience.segments.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AudienceSegmentView {

    private String id;
    private String name;

    @SerializedName("list_id")
    private String listId;

    @SerializedName("list_name")
    private String listName;

    @SerializedName("condition_groups")
    private List<SegmentConditionGroup> conditionGroups;

    @SerializedName("cached_contacts_count")
    private Integer cachedContactsCount;

    @SerializedName("created_at")
    private String createdAt;

    @Nonnull public String getId() { return id; }
    @Nonnull public String getName() { return name; }
    @Nullable public String getListId() { return listId; }
    @Nullable public String getListName() { return listName; }
    @Nonnull public List<SegmentConditionGroup> getConditionGroups() { return conditionGroups; }
    @Nullable public Integer getCachedContactsCount() { return cachedContactsCount; }
    @Nonnull public String getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "AudienceSegmentView{id='" + id + "', name='" + name + "', listId='" + listId + "'}";
    }
}
