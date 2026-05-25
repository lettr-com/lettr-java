package com.lettr.services.audience.topics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.audience.topics.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudienceTopicsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void audienceTopicViewDeserializes() {
        String json = "{\"id\":\"t1\",\"name\":\"Product\",\"description\":\"Product updates\"," +
                "\"default_subscription\":\"opt_in\",\"visibility\":\"public\"," +
                "\"contacts_count\":12,\"created_at\":\"2024-01-15T10:30:00+00:00\"}";

        AudienceTopicView view = gson.fromJson(json, AudienceTopicView.class);
        assertEquals("t1", view.getId());
        assertEquals("Product", view.getName());
        assertEquals("Product updates", view.getDescription());
        assertEquals(AudienceTopicDefaultSubscription.OPT_IN, view.getDefaultSubscription());
        assertEquals(AudienceTopicVisibility.PUBLIC, view.getVisibility());
        assertEquals(12, view.getContactsCount());
    }

    @Test
    void audienceTopicViewWithNullDescription() {
        String json = "{\"id\":\"t1\",\"name\":\"Sales\",\"description\":null," +
                "\"default_subscription\":\"opt_out\",\"visibility\":\"private\"," +
                "\"contacts_count\":0,\"created_at\":\"2024-01-15T10:30:00+00:00\"}";

        AudienceTopicView view = gson.fromJson(json, AudienceTopicView.class);
        assertNull(view.getDescription());
        assertEquals(AudienceTopicDefaultSubscription.OPT_OUT, view.getDefaultSubscription());
        assertEquals(AudienceTopicVisibility.PRIVATE, view.getVisibility());
    }

    @Test
    void listResponseDeserializes() {
        String json = "{\"topics\":[]," +
                "\"pagination\":{\"total\":0,\"per_page\":20,\"current_page\":1,\"last_page\":1}}";
        ListAudienceTopicsResponse response = gson.fromJson(json, ListAudienceTopicsResponse.class);
        assertEquals(0, response.getTopics().size());
    }

    @Test
    void createOptionsRequiresName() {
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudienceTopicOptions.builder().build());
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudienceTopicOptions.builder().name("").build());

        CreateAudienceTopicOptions ok = CreateAudienceTopicOptions.builder()
                .name("Topic")
                .description("Desc")
                .defaultSubscription(AudienceTopicDefaultSubscription.OPT_IN)
                .visibility(AudienceTopicVisibility.PUBLIC)
                .build();
        assertEquals("Topic", ok.getName());
        assertEquals(AudienceTopicVisibility.PUBLIC, ok.getVisibility());
    }

    @Test
    void updateOptionsAllowsEmptyAndOmitsDefaultSubscription() {
        UpdateAudienceTopicOptions empty = UpdateAudienceTopicOptions.builder().build();
        assertNull(empty.getName());
        assertNull(empty.getVisibility());

        UpdateAudienceTopicOptions updated = UpdateAudienceTopicOptions.builder()
                .name("Renamed")
                .visibility(AudienceTopicVisibility.PRIVATE)
                .build();
        assertEquals("Renamed", updated.getName());
    }

    @Test
    void serviceArgumentValidation() {
        AudienceTopics svc = new AudienceTopics("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.get(null));
        assertThrows(IllegalArgumentException.class, () -> svc.get(""));
        assertThrows(IllegalArgumentException.class, () -> svc.create(null));
        assertThrows(IllegalArgumentException.class, () -> svc.update(null, null));
        assertThrows(IllegalArgumentException.class,
                () -> svc.update("", UpdateAudienceTopicOptions.builder().build()));
        assertThrows(IllegalArgumentException.class, () -> svc.update("id", null));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(null));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(""));
    }
}
