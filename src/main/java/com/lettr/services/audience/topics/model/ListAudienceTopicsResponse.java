package com.lettr.services.audience.topics.model;

import com.lettr.services.audience.model.AudiencePagination;

import javax.annotation.Nonnull;
import java.util.List;

public class ListAudienceTopicsResponse {

    private List<AudienceTopicView> topics;
    private AudiencePagination pagination;

    @Nonnull public List<AudienceTopicView> getTopics() { return topics; }
    @Nonnull public AudiencePagination getPagination() { return pagination; }

    @Override
    public String toString() {
        return "ListAudienceTopicsResponse{topics=" + topics + ", pagination=" + pagination + '}';
    }
}
