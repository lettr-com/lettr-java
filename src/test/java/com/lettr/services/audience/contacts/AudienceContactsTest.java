package com.lettr.services.audience.contacts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.audience.contacts.model.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AudienceContactsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void audienceContactViewDeserializes() {
        String json = "{\"id\":\"c1\",\"email\":\"hi@example.com\",\"status\":\"subscribed\"," +
                "\"properties\":{\"first_name\":\"Ada\"}," +
                "\"created_at\":\"2024-01-15T10:30:00+00:00\"," +
                "\"lists\":[{\"id\":\"l1\",\"name\":\"News\"}]," +
                "\"topics\":[{\"id\":\"t1\",\"name\":\"Product\"}]}";

        AudienceContactView view = gson.fromJson(json, AudienceContactView.class);
        assertEquals("c1", view.getId());
        assertEquals("hi@example.com", view.getEmail());
        assertEquals(AudienceContactStatus.SUBSCRIBED, view.getStatus());
        assertEquals("Ada", view.getProperties().get("first_name"));
        assertEquals(1, view.getLists().size());
        assertEquals("News", view.getLists().get(0).getName());
        assertEquals(1, view.getTopics().size());
        assertEquals("t1", view.getTopics().get(0).getId());
    }

    @Test
    void listAudienceContactsResponseDeserializes() {
        String json = "{\"contacts\":[]," +
                "\"pagination\":{\"total\":0,\"per_page\":20,\"current_page\":1,\"last_page\":1}}";

        ListAudienceContactsResponse response = gson.fromJson(json, ListAudienceContactsResponse.class);
        assertNotNull(response.getContacts());
        assertEquals(0, response.getContacts().size());
        assertEquals(0, response.getPagination().getTotal());
    }

    @Test
    void contactStatusEnumRoundtrips() {
        // Verifies all 5 status values deserialize.
        for (String s : Arrays.asList("subscribed", "unsubscribed", "bounced", "complained", "unverified")) {
            AudienceContactStatus parsed = gson.fromJson("\"" + s + "\"", AudienceContactStatus.class);
            assertNotNull(parsed);
        }
    }

    @Test
    void bulkCreateResponseDeserializes() {
        String json = "{\"created\":7,\"already_existed\":3}";
        BulkCreateAudienceContactsResponse response = gson.fromJson(json, BulkCreateAudienceContactsResponse.class);
        assertEquals(7, response.getCreated());
        assertEquals(3, response.getAlreadyExisted());
    }

    @Test
    void bulkAttachResponseDeserializes() {
        String json = "{\"attached\":4,\"already_attached\":2,\"total_pairs\":6}";
        BulkAttachContactsResponse response = gson.fromJson(json, BulkAttachContactsResponse.class);
        assertEquals(4, response.getAttached());
        assertEquals(2, response.getAlreadyAttached());
        assertEquals(6, response.getTotalPairs());
    }

    @Test
    void bulkDetachResponseDeserializes() {
        String json = "{\"detached\":1,\"not_present\":5,\"total_pairs\":6}";
        BulkDetachContactsResponse response = gson.fromJson(json, BulkDetachContactsResponse.class);
        assertEquals(1, response.getDetached());
        assertEquals(5, response.getNotPresent());
        assertEquals(6, response.getTotalPairs());
    }

    @Test
    void createOptionsRequiresEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudienceContactOptions.builder().build());
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudienceContactOptions.builder().email("").build());

        Map<String, String> props = new LinkedHashMap<>();
        props.put("first_name", "Ada");
        CreateAudienceContactOptions ok = CreateAudienceContactOptions.builder()
                .email("ada@example.com")
                .listId("l1")
                .properties(props)
                .build();
        assertEquals("ada@example.com", ok.getEmail());
        assertEquals("l1", ok.getListId());
        assertEquals("Ada", ok.getProperties().get("first_name"));
    }

    @Test
    void doubleOptInBuilderValidatesRequiredFields() {
        assertThrows(IllegalArgumentException.class, () -> DoubleOptInConfig.builder().build());
        assertThrows(IllegalArgumentException.class, () -> DoubleOptInConfig.builder()
                .from("a@b.com").subject("s").templateSlug("t").build()); // missing redirectUrl

        DoubleOptInConfig ok = DoubleOptInConfig.builder()
                .from("a@b.com")
                .subject("Confirm")
                .templateSlug("confirm-template")
                .redirectUrl("https://example.com/thanks")
                .build();
        assertEquals("a@b.com", ok.getFrom());
        assertEquals("Confirm", ok.getSubject());
    }

    @Test
    void bulkCreateOptionsValidatesEmailsBounds() {
        assertThrows(IllegalArgumentException.class,
                () -> BulkCreateAudienceContactsOptions.builder().emails(Collections.emptyList()).build());
        assertNotNull(BulkCreateAudienceContactsOptions.builder()
                .emails(Arrays.asList("a@b.com", "c@d.com")).build());
    }

    @Test
    void bulkContactListsOptionsValidatesBounds() {
        assertThrows(IllegalArgumentException.class,
                () -> BulkContactListsOptions.of(Collections.emptyList(), Arrays.asList("l1")));
        assertThrows(IllegalArgumentException.class,
                () -> BulkContactListsOptions.of(Arrays.asList("c1"), Collections.emptyList()));
        assertNotNull(BulkContactListsOptions.of(Arrays.asList("c1"), Arrays.asList("l1")));
    }

    @Test
    void updateOptionsSerializesNullPropertyValuesAsJsonNull() {
        // The API removes a property when the value is JSON null. Default Gson
        // drops null map values, which would silently turn the deletion into a no-op.
        Map<String, String> props = new LinkedHashMap<>();
        props.put("keep_me", "still here");
        props.put("delete_me", null);

        UpdateAudienceContactOptions options = UpdateAudienceContactOptions.builder()
                .properties(props)
                .build();

        String json = gson.toJson(options);
        assertTrue(json.contains("\"delete_me\":null"),
                "expected delete_me to serialize as JSON null, got: " + json);
        assertTrue(json.contains("\"keep_me\":\"still here\""), json);
    }

    @Test
    void updateOptionsRejectsServerManagedStatuses() {
        // API only accepts subscribed/unsubscribed when updating a contact.
        for (AudienceContactStatus invalid : new AudienceContactStatus[]{
                AudienceContactStatus.BOUNCED,
                AudienceContactStatus.COMPLAINED,
                AudienceContactStatus.UNVERIFIED}) {
            assertThrows(IllegalArgumentException.class,
                    () -> UpdateAudienceContactOptions.builder().status(invalid).build(),
                    "status " + invalid + " should be rejected");
        }

        UpdateAudienceContactOptions ok = UpdateAudienceContactOptions.builder()
                .status(AudienceContactStatus.SUBSCRIBED).build();
        assertEquals(AudienceContactStatus.SUBSCRIBED, ok.getStatus());

        UpdateAudienceContactOptions nullOk = UpdateAudienceContactOptions.builder()
                .status(null).build();
        assertNull(nullOk.getStatus());
    }

    @Test
    void serviceArgumentValidation() {
        AudienceContacts svc = new AudienceContacts("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.get(null));
        assertThrows(IllegalArgumentException.class, () -> svc.get(""));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(null));
        assertThrows(IllegalArgumentException.class, () -> svc.create(null));
        assertThrows(IllegalArgumentException.class, () -> svc.bulkCreate(null));
        assertThrows(IllegalArgumentException.class, () -> svc.update(null, null));
        assertThrows(IllegalArgumentException.class, () -> svc.attachToList(null, "l1"));
        assertThrows(IllegalArgumentException.class, () -> svc.attachToList("c1", ""));
        assertThrows(IllegalArgumentException.class, () -> svc.detachFromList("", "l1"));
        assertThrows(IllegalArgumentException.class, () -> svc.subscribeToTopic("c1", null));
        assertThrows(IllegalArgumentException.class, () -> svc.unsubscribeFromTopic(null, "t1"));
        assertThrows(IllegalArgumentException.class, () -> svc.bulkAttachToLists(null));
        assertThrows(IllegalArgumentException.class, () -> svc.bulkDetachFromLists(null));
    }
}
