package com.lettr.services.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.system.model.AuthCheckResponse;
import com.lettr.services.system.model.HealthResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void healthResponseDeserializes() {
        String json = "{\"status\":\"ok\",\"timestamp\":\"2024-01-15T10:30:00.000Z\"}";
        HealthResponse response = gson.fromJson(json, HealthResponse.class);
        assertEquals("ok", response.getStatus());
        assertEquals("2024-01-15T10:30:00.000Z", response.getTimestamp());
    }

    @Test
    void authCheckResponseDeserializes() {
        String json = "{\"team_id\":123,\"timestamp\":\"2024-01-15T10:30:00.000Z\"}";
        AuthCheckResponse response = gson.fromJson(json, AuthCheckResponse.class);
        assertEquals(123, response.getTeamId());
        assertEquals("2024-01-15T10:30:00.000Z", response.getTimestamp());
    }

    @Test
    void systemServiceConstructable() {
        System system = new System("test-key");
        assertNotNull(system);
    }
}
