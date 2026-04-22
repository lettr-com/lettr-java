package com.lettr.services.webhooks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.webhooks.model.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WebhooksTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    // --- CreateWebhookOptions tests ---

    @Test
    void createWebhookOptionsRequiresName() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateWebhookOptions.builder()
                        .url("https://example.com/webhook")
                        .authType("none")
                        .eventsMode("all")
                        .build());
    }

    @Test
    void createWebhookOptionsRequiresUrl() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateWebhookOptions.builder()
                        .name("Test")
                        .authType("none")
                        .eventsMode("all")
                        .build());
    }

    @Test
    void createWebhookOptionsRequiresAuthType() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateWebhookOptions.builder()
                        .name("Test")
                        .url("https://example.com/webhook")
                        .eventsMode("all")
                        .build());
    }

    @Test
    void createWebhookOptionsRequiresEventsMode() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateWebhookOptions.builder()
                        .name("Test")
                        .url("https://example.com/webhook")
                        .authType("none")
                        .build());
    }

    @Test
    void createWebhookOptionsBuildsWithAllFields() {
        CreateWebhookOptions options = CreateWebhookOptions.builder()
                .name("Test Webhook")
                .url("https://example.com/webhook")
                .authType("basic")
                .authUsername("user")
                .authPassword("pass")
                .eventsMode("selected")
                .events(Arrays.asList("message.delivery", "message.bounce"))
                .build();

        assertEquals("Test Webhook", options.getName());
        assertEquals("https://example.com/webhook", options.getUrl());
        assertEquals("basic", options.getAuthType());
        assertEquals("user", options.getAuthUsername());
        assertEquals("pass", options.getAuthPassword());
        assertEquals("selected", options.getEventsMode());
        assertEquals(2, options.getEvents().size());
    }

    @Test
    void createWebhookOptionsSerializesCorrectFieldNames() {
        CreateWebhookOptions options = CreateWebhookOptions.builder()
                .name("Test")
                .url("https://example.com/webhook")
                .authType("basic")
                .authUsername("user")
                .authPassword("pass")
                .eventsMode("all")
                .build();

        String json = gson.toJson(options);
        assertTrue(json.contains("\"auth_type\""));
        assertTrue(json.contains("\"auth_username\""));
        assertTrue(json.contains("\"auth_password\""));
        assertTrue(json.contains("\"events_mode\""));
    }

    // --- UpdateWebhookOptions tests ---

    @Test
    void updateWebhookOptionsBuildsWithNoFields() {
        UpdateWebhookOptions options = UpdateWebhookOptions.builder().build();
        assertNotNull(options);
    }

    @Test
    void updateWebhookOptionsBuildsWithAllFields() {
        UpdateWebhookOptions options = UpdateWebhookOptions.builder()
                .name("Updated")
                .url("https://new.example.com/webhook")
                .authType("oauth2")
                .oauthClientId("client-id")
                .oauthClientSecret("secret")
                .oauthTokenUrl("https://auth.example.com/token")
                .events(Arrays.asList("message.delivery"))
                .active(false)
                .build();

        assertEquals("Updated", options.getName());
        assertEquals("https://new.example.com/webhook", options.getUrl());
        assertEquals("oauth2", options.getAuthType());
        assertEquals(false, options.getActive());
    }

    @Test
    @SuppressWarnings("deprecation")
    void updateWebhookOptionsDeprecatedTargetStillWorks() {
        UpdateWebhookOptions options = UpdateWebhookOptions.builder()
                .target("https://legacy.example.com/webhook")
                .build();

        assertEquals("https://legacy.example.com/webhook", options.getUrl());
        assertEquals("https://legacy.example.com/webhook", options.getTarget());
    }

    @Test
    void updateWebhookOptionsSerializesUrlNotTarget() {
        UpdateWebhookOptions options = UpdateWebhookOptions.builder()
                .url("https://new.example.com/webhook")
                .build();

        String json = gson.toJson(options);
        assertTrue(json.contains("\"url\""));
        assertFalse(json.contains("\"target\""));
    }

    @Test
    @SuppressWarnings("deprecation")
    void updateWebhookOptionsDeprecatedTargetSerializesAsUrl() {
        UpdateWebhookOptions options = UpdateWebhookOptions.builder()
                .target("https://legacy.example.com/webhook")
                .build();

        String json = gson.toJson(options);
        assertTrue(json.contains("\"url\""));
        assertFalse(json.contains("\"target\""));
    }

    // --- Webhook deserialization ---

    @Test
    void webhookDeserializes() {
        String json = "{\"id\":\"wh-123\",\"name\":\"Test\",\"url\":\"https://example.com/webhook\"," +
                "\"enabled\":true,\"event_types\":[\"message.delivery\",\"message.bounce\"]," +
                "\"auth_type\":\"basic\",\"has_auth_credentials\":true," +
                "\"last_successful_at\":\"2024-01-15T10:30:00+00:00\"," +
                "\"last_failure_at\":null,\"last_status\":\"success\"}";

        Webhook webhook = gson.fromJson(json, Webhook.class);
        assertEquals("wh-123", webhook.getId());
        assertEquals("Test", webhook.getName());
        assertTrue(webhook.isEnabled());
        assertEquals(2, webhook.getEventTypes().size());
        assertEquals("basic", webhook.getAuthType());
        assertTrue(webhook.isHasAuthCredentials());
        assertEquals("success", webhook.getLastStatus());
        assertNull(webhook.getLastFailureAt());
    }

    @Test
    void listWebhooksResponseDeserializes() {
        String json = "{\"webhooks\":[{\"id\":\"wh-1\",\"name\":\"Test\",\"url\":\"https://example.com\"," +
                "\"enabled\":true,\"auth_type\":\"none\",\"has_auth_credentials\":false}]}";

        ListWebhooksResponse response = gson.fromJson(json, ListWebhooksResponse.class);
        assertEquals(1, response.getWebhooks().size());
        assertEquals("wh-1", response.getWebhooks().get(0).getId());
    }

    // --- Service argument validation ---

    @Test
    void webhooksGetRequiresId() {
        Webhooks webhooks = new Webhooks("test-key");
        assertThrows(IllegalArgumentException.class, () -> webhooks.get(null));
        assertThrows(IllegalArgumentException.class, () -> webhooks.get(""));
    }

    @Test
    void webhooksUpdateRequiresId() {
        Webhooks webhooks = new Webhooks("test-key");
        UpdateWebhookOptions options = UpdateWebhookOptions.builder().name("x").build();
        assertThrows(IllegalArgumentException.class, () -> webhooks.update(null, options));
        assertThrows(IllegalArgumentException.class, () -> webhooks.update("", options));
    }

    @Test
    void webhooksDeleteRequiresId() {
        Webhooks webhooks = new Webhooks("test-key");
        assertThrows(IllegalArgumentException.class, () -> webhooks.delete(null));
        assertThrows(IllegalArgumentException.class, () -> webhooks.delete(""));
    }
}
