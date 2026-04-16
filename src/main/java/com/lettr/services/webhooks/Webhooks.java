package com.lettr.services.webhooks;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.webhooks.model.CreateWebhookOptions;
import com.lettr.services.webhooks.model.ListWebhooksResponse;
import com.lettr.services.webhooks.model.UpdateWebhookOptions;
import com.lettr.services.webhooks.model.Webhook;

import javax.annotation.Nonnull;

/**
 * Service for managing webhooks via the Lettr API.
 */
public class Webhooks extends BaseService {

    public Webhooks(@Nonnull String apiKey) {
        super(apiKey);
    }

    /** List all configured webhooks. */
    @Nonnull
    public ListWebhooksResponse list() throws LettrException {
        return httpClient.get("/webhooks", null, ListWebhooksResponse.class);
    }

    /**
     * Get details of a specific webhook.
     *
     * @throws IllegalArgumentException if {@code webhookId} is null or empty
     */
    @Nonnull
    public Webhook get(@Nonnull String webhookId) throws LettrException {
        if (webhookId == null || webhookId.isEmpty()) {
            throw new IllegalArgumentException("webhookId is required");
        }
        return httpClient.get("/webhooks/" + webhookId, null, Webhook.class);
    }

    /** Create a new webhook. */
    @Nonnull
    public Webhook create(@Nonnull CreateWebhookOptions options) throws LettrException {
        return httpClient.post("/webhooks", options, Webhook.class);
    }

    /**
     * Update an existing webhook.
     *
     * @throws IllegalArgumentException if {@code webhookId} is null or empty
     */
    @Nonnull
    public Webhook update(@Nonnull String webhookId, @Nonnull UpdateWebhookOptions options) throws LettrException {
        if (webhookId == null || webhookId.isEmpty()) {
            throw new IllegalArgumentException("webhookId is required");
        }
        return httpClient.put("/webhooks/" + webhookId, options, Webhook.class);
    }

    /**
     * Delete a webhook.
     *
     * @throws IllegalArgumentException if {@code webhookId} is null or empty
     */
    public void delete(@Nonnull String webhookId) throws LettrException {
        if (webhookId == null || webhookId.isEmpty()) {
            throw new IllegalArgumentException("webhookId is required");
        }
        httpClient.delete("/webhooks/" + webhookId);
    }
}
