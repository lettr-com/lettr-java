package com.lettr.services.audience.properties.model;

import com.lettr.services.audience.model.AudiencePagination;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class ListAudiencePropertiesResponse {

    private List<AudiencePropertyView> properties;
    private AudiencePagination pagination;

    @Nonnull public List<AudiencePropertyView> getProperties() { return properties != null ? properties : Collections.emptyList(); }
    @Nonnull public AudiencePagination getPagination() { return pagination; }

    @Override
    public String toString() {
        return "ListAudiencePropertiesResponse{properties=" + properties + ", pagination=" + pagination + '}';
    }
}
