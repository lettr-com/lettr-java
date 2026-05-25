package com.lettr.services.audience.topics;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.audience.model.PageParams;
import com.lettr.services.audience.topics.model.AudienceTopicView;
import com.lettr.services.audience.topics.model.CreateAudienceTopicOptions;
import com.lettr.services.audience.topics.model.ListAudienceTopicsResponse;
import com.lettr.services.audience.topics.model.UpdateAudienceTopicOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Service for managing audience topics.
 */
public class AudienceTopics extends BaseService {

    public AudienceTopics(@Nonnull String apiKey) {
        super(apiKey);
    }

    @Nonnull
    public ListAudienceTopicsResponse list() throws LettrException {
        return list(null);
    }

    @Nonnull
    public ListAudienceTopicsResponse list(@Nullable PageParams params) throws LettrException {
        return httpClient.get("/audience/topics",
                params != null ? params.toQueryParams() : null,
                ListAudienceTopicsResponse.class);
    }

    @Nonnull
    public AudienceTopicView get(@Nonnull String topicId) throws LettrException {
        if (topicId == null || topicId.isEmpty()) {
            throw new IllegalArgumentException("topicId is required");
        }
        return httpClient.get("/audience/topics/" + topicId, null, AudienceTopicView.class);
    }

    @Nonnull
    public AudienceTopicView create(@Nonnull CreateAudienceTopicOptions options) throws LettrException {
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.post("/audience/topics", options, AudienceTopicView.class);
    }

    @Nonnull
    public AudienceTopicView update(@Nonnull String topicId, @Nonnull UpdateAudienceTopicOptions options) throws LettrException {
        if (topicId == null || topicId.isEmpty()) {
            throw new IllegalArgumentException("topicId is required");
        }
        if (options == null) {
            throw new IllegalArgumentException("options is required");
        }
        return httpClient.patch("/audience/topics/" + topicId, options, AudienceTopicView.class);
    }

    public void delete(@Nonnull String topicId) throws LettrException {
        if (topicId == null || topicId.isEmpty()) {
            throw new IllegalArgumentException("topicId is required");
        }
        httpClient.delete("/audience/topics/" + topicId);
    }
}
