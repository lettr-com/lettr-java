package com.lettr.services.audience.contacts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.core.exception.ContactAlreadyExistsException;
import com.lettr.core.exception.LettrApiException;
import com.lettr.core.exception.LettrException;
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

    // --- TPL-2105: bulk contact import ---

    @Test
    void bulkCreateOptionsRequiresEitherEmailsOrContacts() {
        assertThrows(IllegalArgumentException.class,
                () -> BulkCreateAudienceContactsOptions.builder().listId("l1").build());

        // Either shape on its own is enough.
        assertNotNull(BulkCreateAudienceContactsOptions.builder()
                .emails(Arrays.asList("a@b.com")).build());
        assertNotNull(BulkCreateAudienceContactsOptions.builder()
                .contacts(Arrays.asList(BulkAudienceContactRow.of("a@b.com"))).build());
    }

    @Test
    void bulkCreateOptionsKeepsTheLegacyPayloadByteIdentical() {
        // A pre-TPL-2105 call must serialize exactly as it did before: no
        // "contacts" key, and no "update_existing" unless it was asked for.
        String json = gson.toJson(BulkCreateAudienceContactsOptions.builder()
                .emails(Arrays.asList("a@b.com", "c@d.com"))
                .listId("l1")
                .build());

        assertEquals("{\"emails\":[\"a@b.com\",\"c@d.com\"],\"list_id\":\"l1\"}", json);
    }

    @Test
    void bulkCreateOptionsSerializesPerContactRows() {
        Map<String, String> rowProps = new LinkedHashMap<>();
        rowProps.put("plan", "pro");

        BulkCreateAudienceContactsOptions options = BulkCreateAudienceContactsOptions.builder()
                .contacts(Arrays.asList(
                        BulkAudienceContactRow.builder()
                                .email("cara@example.com")
                                .properties(rowProps)
                                .listIds(Arrays.asList("l-vip"))
                                .build(),
                        // Row-level opt-out must beat the batch-wide opt-in below.
                        BulkAudienceContactRow.builder()
                                .email("dan@example.com")
                                .topic(AudienceTopicSubscription.optOut("t-promos"))
                                .build()))
                .listIds(Arrays.asList("l-everyone"))
                .topics(Arrays.asList(AudienceTopicSubscription.optIn("t-promos")))
                .updateExisting(true)
                .build();

        String json = gson.toJson(options);

        assertFalse(json.contains("\"emails\""), json);
        assertTrue(json.contains("\"email\":\"cara@example.com\""), json);
        assertTrue(json.contains("\"list_ids\":[\"l-vip\"]"), json);
        assertTrue(json.contains("\"topics\":[{\"id\":\"t-promos\",\"subscription\":\"opt_out\"}]"), json);
        assertTrue(json.contains("\"list_ids\":[\"l-everyone\"]"), json);
        assertTrue(json.contains("\"subscription\":\"opt_in\""), json);
        assertTrue(json.contains("\"update_existing\":true"), json);
    }

    @Test
    void topicSubscriptionRejectsMissingId() {
        assertThrows(IllegalArgumentException.class, () -> AudienceTopicSubscription.optIn(""));
        assertThrows(IllegalArgumentException.class, () -> AudienceTopicSubscription.optOut(null));
        assertEquals(AudienceTopicSubscriptionState.OPT_OUT,
                AudienceTopicSubscription.optOut("t1").getSubscription());
    }

    @Test
    void bulkCreateResponseDeserializesTheNewFields() {
        String json = "{\"created\":2,\"already_existed\":1,\"updated\":1,\"error_count\":0,"
                + "\"errors\":[],"
                + "\"contacts\":[{\"id\":\"c1\",\"email\":\"Cara@example.com\",\"created\":true},"
                + "{\"id\":\"c2\",\"email\":\"dan@example.com\",\"created\":false}]}";

        BulkCreateAudienceContactsResponse response =
                gson.fromJson(json, BulkCreateAudienceContactsResponse.class);

        assertEquals(1, response.getUpdated());
        assertEquals(0, response.getErrorCount());
        assertFalse(response.hasErrors());
        // Ids come back in submission order, so no follow-up lookup is needed.
        assertEquals(Arrays.asList("c1", "c2"), response.getContactIds());
        assertFalse(response.getContacts().get(1).isCreated());
        // findIdFor is case-insensitive: the API normalizes addresses.
        assertEquals("c1", response.findIdFor("  cara@EXAMPLE.com "));
        assertNull(response.findIdFor("nobody@example.com"));
    }

    @Test
    void bulkCreateResponseTreatsOmittedFieldsAsEmpty() {
        // An API deployment older than TPL-2105 answers with just the two
        // counters. hasErrors() and getContactIds() must still be usable.
        BulkCreateAudienceContactsResponse response = gson.fromJson(
                "{\"created\":2,\"already_existed\":1}", BulkCreateAudienceContactsResponse.class);

        assertEquals(0, response.getUpdated());
        assertFalse(response.hasErrors());
        assertNotNull(response.getErrors());
        assertTrue(response.getContactIds().isEmpty());
    }

    @Test
    void bulkCreateResponseReportsSkippedRows() {
        // Partial success: HTTP 201 with errors populated. Nothing throws, even
        // though one row never landed — that is the trap this pins down.
        String json = "{\"created\":1,\"already_existed\":0,\"updated\":0,\"error_count\":1,"
                + "\"errors\":[{\"index\":1,\"email\":\"not-an-email\","
                + "\"error_code\":\"invalid_email\",\"error\":\"The email address is not valid.\"}],"
                + "\"contacts\":[{\"id\":\"c1\",\"email\":\"cara@example.com\",\"created\":true}]}";

        BulkCreateAudienceContactsResponse response =
                gson.fromJson(json, BulkCreateAudienceContactsResponse.class);

        assertTrue(response.hasErrors());
        assertEquals(1, response.getErrorCount());
        assertEquals(1, response.getErrors().get(0).getIndex());
        assertEquals(BulkAudienceContactErrorCode.INVALID_EMAIL, response.getErrors().get(0).getCode());
        assertEquals(1, response.getContacts().size());
    }

    @Test
    void bulkContactErrorSurvivesAnUnknownCode() {
        // A code added server-side must stay readable rather than failing to parse.
        String json = "{\"created\":0,\"already_existed\":0,\"error_count\":1,"
                + "\"errors\":[{\"index\":0,\"email\":null,"
                + "\"error_code\":\"some_future_code\",\"error\":\"Nope.\"}]}";

        BulkCreateAudienceContactsResponse response =
                gson.fromJson(json, BulkCreateAudienceContactsResponse.class);

        assertEquals("some_future_code", response.getErrors().get(0).getErrorCode());
        assertNull(response.getErrors().get(0).getCode());
        assertNull(response.getErrors().get(0).getEmail());
    }

    @Test
    void bulkContactTopicsOptionsValidatesBounds() {
        assertThrows(IllegalArgumentException.class,
                () -> BulkContactTopicsOptions.of(Collections.emptyList(), Arrays.asList("t1")));
        assertThrows(IllegalArgumentException.class,
                () -> BulkContactTopicsOptions.of(Arrays.asList("c1"), Collections.emptyList()));

        BulkContactTopicsOptions options = BulkContactTopicsOptions.of(
                Arrays.asList("c1", "c2"), Arrays.asList("t1", "t2"));
        assertEquals("{\"contact_ids\":[\"c1\",\"c2\"],\"topic_ids\":[\"t1\",\"t2\"]}",
                gson.toJson(options));
    }

    @Test
    void bulkTopicResponsesDeserialize() {
        BulkSubscribeContactsResponse subscribed = gson.fromJson(
                "{\"subscribed\":3,\"already_subscribed\":1,\"total_pairs\":4}",
                BulkSubscribeContactsResponse.class);
        // 2 contacts × 2 topics — the endpoint works over the cartesian product.
        assertEquals(3, subscribed.getSubscribed());
        assertEquals(1, subscribed.getAlreadySubscribed());
        assertEquals(4, subscribed.getTotalPairs());

        BulkUnsubscribeContactsResponse unsubscribed = gson.fromJson(
                "{\"unsubscribed\":2,\"total_pairs\":4}",
                BulkUnsubscribeContactsResponse.class);
        // Pairs that did not exist are ignored, so this is below totalPairs.
        assertEquals(2, unsubscribed.getUnsubscribed());
        assertEquals(4, unsubscribed.getTotalPairs());
    }

    @Test
    void contactAlreadyExistsExceptionIsAnApiException() {
        // The 409 replaces a 500 send_error. Subclassing LettrApiException keeps
        // pre-existing catch blocks working.
        ContactAlreadyExistsException e = new ContactAlreadyExistsException(
                "A contact with the email jane@example.com already exists.",
                409, "resource_already_exists", "jane@example.com");

        assertTrue(e instanceof LettrApiException);
        assertTrue(e instanceof LettrException);
        assertEquals(409, e.getStatusCode());
        assertEquals("resource_already_exists", e.getErrorCode());
        assertEquals("jane@example.com", e.getEmail());
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
        assertThrows(IllegalArgumentException.class, () -> svc.bulkSubscribeToTopics(null));
        assertThrows(IllegalArgumentException.class, () -> svc.bulkUnsubscribeFromTopics(null));
    }
}
