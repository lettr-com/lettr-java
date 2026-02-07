package com.lettr.services.webhooks.model;

import java.util.List;

/**
 * Response from listing webhooks.
 */
public class ListWebhooksResponse {

    private List<Webhook> webhooks;

    /**
     * Returns the list of configured webhooks.
     */
    public List<Webhook> getWebhooks() {
        return webhooks;
    }

    @Override
    public String toString() {
        return "ListWebhooksResponse{" +
                "webhooks=" + webhooks +
                '}';
    }
}
