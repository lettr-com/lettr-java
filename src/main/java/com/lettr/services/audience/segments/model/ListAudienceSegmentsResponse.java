package com.lettr.services.audience.segments.model;

import com.lettr.services.audience.model.AudiencePagination;

import javax.annotation.Nonnull;
import java.util.List;

public class ListAudienceSegmentsResponse {

    private List<AudienceSegmentView> segments;
    private AudiencePagination pagination;

    @Nonnull public List<AudienceSegmentView> getSegments() { return segments; }
    @Nonnull public AudiencePagination getPagination() { return pagination; }

    @Override
    public String toString() {
        return "ListAudienceSegmentsResponse{segments=" + segments + ", pagination=" + pagination + '}';
    }
}
