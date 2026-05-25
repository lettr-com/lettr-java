package com.lettr.services.audience.segments;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.audience.segments.model.AudienceSegmentView;
import com.lettr.services.audience.segments.model.CreateAudienceSegmentOptions;
import com.lettr.services.audience.segments.model.ListAudienceSegmentsParams;
import com.lettr.services.audience.segments.model.ListAudienceSegmentsResponse;
import com.lettr.services.audience.segments.model.UpdateAudienceSegmentOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for managing audience segments.
 */
public class AudienceSegments extends BaseService {

    public AudienceSegments(@Nonnull String apiKey) {
        super(apiKey);
    }

    @Nonnull
    public ListAudienceSegmentsResponse list() throws LettrException {
        return list(null);
    }

    @Nonnull
    public ListAudienceSegmentsResponse list(@Nullable ListAudienceSegmentsParams params) throws LettrException {
        return httpClient.get("/audience/segments",
                params != null ? params.toQueryParams() : null,
                ListAudienceSegmentsResponse.class);
    }

    @Nonnull
    public AudienceSegmentView get(@Nonnull String segmentId) throws LettrException {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("segmentId is required");
        }
        return httpClient.get("/audience/segments/" + segmentId, null, AudienceSegmentView.class);
    }

    @Nonnull
    public AudienceSegmentView create(@Nonnull CreateAudienceSegmentOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.post("/audience/segments", options, AudienceSegmentView.class);
    }

    @Nonnull
    public AudienceSegmentView update(@Nonnull String segmentId, @Nonnull UpdateAudienceSegmentOptions options) throws LettrException {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("segmentId is required");
        }
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.patch("/audience/segments/" + segmentId, options, AudienceSegmentView.class);
    }

    public void delete(@Nonnull String segmentId) throws LettrException {
        if (segmentId == null || segmentId.isEmpty()) {
            throw new IllegalArgumentException("segmentId is required");
        }
        httpClient.delete("/audience/segments/" + segmentId);
    }
}
