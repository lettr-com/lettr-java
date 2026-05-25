package com.lettr.services.audience.properties;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.audience.model.PageParams;
import com.lettr.services.audience.properties.model.AudiencePropertyView;
import com.lettr.services.audience.properties.model.CreateAudiencePropertyOptions;
import com.lettr.services.audience.properties.model.ListAudiencePropertiesResponse;
import com.lettr.services.audience.properties.model.UpdateAudiencePropertyOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for managing custom audience contact properties.
 */
public class AudienceProperties extends BaseService {

    public AudienceProperties(@Nonnull String apiKey) {
        super(apiKey);
    }

    @Nonnull
    public ListAudiencePropertiesResponse list() throws LettrException {
        return list(null);
    }

    @Nonnull
    public ListAudiencePropertiesResponse list(@Nullable PageParams params) throws LettrException {
        return httpClient.get("/audience/properties",
                params != null ? params.toQueryParams() : null,
                ListAudiencePropertiesResponse.class);
    }

    @Nonnull
    public AudiencePropertyView get(@Nonnull String propertyId) throws LettrException {
        if (propertyId == null || propertyId.isEmpty()) {
            throw new IllegalArgumentException("propertyId is required");
        }
        return httpClient.get("/audience/properties/" + propertyId, null, AudiencePropertyView.class);
    }

    @Nonnull
    public AudiencePropertyView create(@Nonnull CreateAudiencePropertyOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.post("/audience/properties", options, AudiencePropertyView.class);
    }

    @Nonnull
    public AudiencePropertyView update(@Nonnull String propertyId, @Nonnull UpdateAudiencePropertyOptions options) throws LettrException {
        if (propertyId == null || propertyId.isEmpty()) {
            throw new IllegalArgumentException("propertyId is required");
        }
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.patch("/audience/properties/" + propertyId, options, AudiencePropertyView.class);
    }

    public void delete(@Nonnull String propertyId) throws LettrException {
        if (propertyId == null || propertyId.isEmpty()) {
            throw new IllegalArgumentException("propertyId is required");
        }
        httpClient.delete("/audience/properties/" + propertyId);
    }
}
