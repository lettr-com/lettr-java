package com.lettr.services.audience.lists.model;

import com.lettr.services.audience.model.AudiencePagination;

import javax.annotation.Nonnull;
import java.util.List;

public class ListAudienceListsResponse {

    private List<AudienceListView> lists;
    private AudiencePagination pagination;

    @Nonnull
    public List<AudienceListView> getLists() {
        return lists;
    }

    @Nonnull
    public AudiencePagination getPagination() {
        return pagination;
    }

    @Override
    public String toString() {
        return "ListAudienceListsResponse{lists=" + lists + ", pagination=" + pagination + '}';
    }
}
