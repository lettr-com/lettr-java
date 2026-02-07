package com.lettr.services.webhooks;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.webhooks.model.ListWebhooksResponse;
import com.lettr.services.webhooks.model.Webhook;

/**
 * Service for managing webhooks via the Lettr API.
 *
 * <p>Example:</p>
 * <pre>{@code
 * Lettr lettr = new Lettr("your-api-key");
 *
 * // List all webhooks
 * ListWebhooksResponse webhooks = lettr.webhooks().list();
 *
 * // Get a specific webhook
 * Webhook webhook = lettr.webhooks().get("webhook-abc123");
 * }</pre>
 */
public class Webhooks extends BaseService {

    public Webhooks(String apiKey) {
        super(apiKey);
    }

    /**
     * List all configured webhooks.
     *
     * @return response containing list of webhooks
     * @throws LettrException if the request fails
     */
    public ListWebhooksResponse list() throws LettrException {
        return httpClient.get("/webhooks", null, ListWebhooksResponse.class);
    }

    /**
     * Get details of a specific webhook.
     *
     * @param webhookId the webhook ID
     * @return webhook details
     * @throws LettrException if the request fails
     */
    public Webhook get(String webhookId) throws LettrException {
        if (webhookId == null || webhookId.isEmpty()) {
            throw new IllegalArgumentException("webhookId is required");
        }
        return httpClient.get("/webhooks/" + webhookId, null, Webhook.class);
    }
}
