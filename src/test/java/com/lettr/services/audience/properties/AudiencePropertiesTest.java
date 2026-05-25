package com.lettr.services.audience.properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.audience.properties.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudiencePropertiesTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void audiencePropertyViewDeserializes() {
        String json = "{\"id\":\"p1\",\"name\":\"first_name\",\"type\":\"string\"," +
                "\"fallback_value\":\"there\",\"created_at\":\"2024-01-15T10:30:00+00:00\"}";

        AudiencePropertyView view = gson.fromJson(json, AudiencePropertyView.class);
        assertEquals("p1", view.getId());
        assertEquals("first_name", view.getName());
        assertEquals(AudiencePropertyType.STRING, view.getType());
        assertEquals("there", view.getFallbackValue());
    }

    @Test
    void audiencePropertyViewWithNullFallback() {
        String json = "{\"id\":\"p2\",\"name\":\"signup_count\",\"type\":\"number\"," +
                "\"fallback_value\":null,\"created_at\":\"2024-01-15T10:30:00+00:00\"}";

        AudiencePropertyView view = gson.fromJson(json, AudiencePropertyView.class);
        assertEquals(AudiencePropertyType.NUMBER, view.getType());
        assertNull(view.getFallbackValue());
    }

    @Test
    void listResponseDeserializes() {
        String json = "{\"properties\":[{\"id\":\"p1\",\"name\":\"first_name\"," +
                "\"type\":\"string\",\"fallback_value\":null," +
                "\"created_at\":\"2024-01-15T10:30:00+00:00\"}]," +
                "\"pagination\":{\"total\":1,\"per_page\":20,\"current_page\":1,\"last_page\":1}}";

        ListAudiencePropertiesResponse response = gson.fromJson(json, ListAudiencePropertiesResponse.class);
        assertEquals(1, response.getProperties().size());
        assertEquals(AudiencePropertyType.STRING, response.getProperties().get(0).getType());
    }

    @Test
    void createOptionsRequiresNameAndType() {
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudiencePropertyOptions.builder().build());
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudiencePropertyOptions.builder().name("first_name").build());
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudiencePropertyOptions.builder().type(AudiencePropertyType.STRING).build());

        CreateAudiencePropertyOptions ok = CreateAudiencePropertyOptions.builder()
                .name("first_name")
                .type(AudiencePropertyType.STRING)
                .fallbackValue("there")
                .build();
        assertEquals("first_name", ok.getName());
        assertEquals(AudiencePropertyType.STRING, ok.getType());
        assertEquals("there", ok.getFallbackValue());
    }

    @Test
    void updateOptionsExposesOnlyFallback() {
        UpdateAudiencePropertyOptions cleared = UpdateAudiencePropertyOptions.withFallbackValue(null);
        assertNull(cleared.getFallbackValue());

        UpdateAudiencePropertyOptions set = UpdateAudiencePropertyOptions.withFallbackValue("hello");
        assertEquals("hello", set.getFallbackValue());
    }

    @Test
    void updateOptionsSerializesNullFallbackAsJsonNull() {
        // To CLEAR the fallback, the API requires {"fallback_value": null}.
        // The default Gson behaviour drops null fields, so without the custom
        // adapter the PATCH would be {} and the server would return the
        // property unchanged.
        String jsonNull = gson.toJson(UpdateAudiencePropertyOptions.withFallbackValue(null));
        assertEquals("{\"fallback_value\":null}", jsonNull);

        String jsonValue = gson.toJson(UpdateAudiencePropertyOptions.withFallbackValue("hi"));
        assertEquals("{\"fallback_value\":\"hi\"}", jsonValue);
    }

    @Test
    void serviceArgumentValidation() {
        AudienceProperties svc = new AudienceProperties("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.get(null));
        assertThrows(IllegalArgumentException.class, () -> svc.get(""));
        assertThrows(IllegalArgumentException.class, () -> svc.create(null));
        assertThrows(IllegalArgumentException.class, () -> svc.update(null, null));
        assertThrows(IllegalArgumentException.class,
                () -> svc.update("", UpdateAudiencePropertyOptions.withFallbackValue("x")));
        assertThrows(IllegalArgumentException.class, () -> svc.update("id", null));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(null));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(""));
    }
}
