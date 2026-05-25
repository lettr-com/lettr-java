package com.lettr.services.audience.segments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.audience.segments.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudienceSegmentsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void audienceSegmentViewDeserializes() {
        String json = "{\"id\":\"s1\",\"name\":\"VIPs\",\"list_id\":\"l1\",\"list_name\":\"News\"," +
                "\"condition_groups\":[{\"conditions\":[" +
                "{\"field\":\"email\",\"operator\":\"contains\",\"value\":\"@acme.com\"}]}]," +
                "\"cached_contacts_count\":17," +
                "\"created_at\":\"2024-01-15T10:30:00+00:00\"}";

        AudienceSegmentView view = gson.fromJson(json, AudienceSegmentView.class);
        assertEquals("s1", view.getId());
        assertEquals("VIPs", view.getName());
        assertEquals("l1", view.getListId());
        assertEquals("News", view.getListName());
        assertEquals(17, view.getCachedContactsCount());
        assertEquals(1, view.getConditionGroups().size());
        SegmentCondition cond = view.getConditionGroups().get(0).getConditions().get(0);
        assertEquals("email", cond.getField());
        assertEquals(SegmentOperator.CONTAINS, cond.getOperator());
        assertEquals("@acme.com", cond.getValue());
    }

    @Test
    void audienceSegmentViewHandlesNulls() {
        String json = "{\"id\":\"s2\",\"name\":\"All\",\"list_id\":null,\"list_name\":null," +
                "\"condition_groups\":[{\"conditions\":[" +
                "{\"field\":\"active\",\"operator\":\"is_true\",\"value\":null}]}]," +
                "\"cached_contacts_count\":null," +
                "\"created_at\":\"2024-01-15T10:30:00+00:00\"}";

        AudienceSegmentView view = gson.fromJson(json, AudienceSegmentView.class);
        assertNull(view.getListId());
        assertNull(view.getListName());
        assertNull(view.getCachedContactsCount());
        assertNull(view.getConditionGroups().get(0).getConditions().get(0).getValue());
        assertEquals(SegmentOperator.IS_TRUE,
                view.getConditionGroups().get(0).getConditions().get(0).getOperator());
    }

    @Test
    void listResponseDeserializes() {
        String json = "{\"segments\":[]," +
                "\"pagination\":{\"total\":0,\"per_page\":20,\"current_page\":1,\"last_page\":1}}";
        ListAudienceSegmentsResponse response = gson.fromJson(json, ListAudienceSegmentsResponse.class);
        assertEquals(0, response.getSegments().size());
    }

    @Test
    void conditionFactoriesValidateInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> SegmentCondition.of(null, SegmentOperator.EQUALS, "x"));
        assertThrows(IllegalArgumentException.class,
                () -> SegmentCondition.of("", SegmentOperator.EQUALS, "x"));
        assertThrows(IllegalArgumentException.class,
                () -> SegmentCondition.of("field", null, "x"));

        SegmentCondition cond = SegmentCondition.of("email", SegmentOperator.EQUALS, "a@b.com");
        assertEquals("email", cond.getField());

        SegmentCondition flag = SegmentCondition.of("active", SegmentOperator.IS_TRUE);
        assertNull(flag.getValue());
    }

    @Test
    void conditionGroupFactoryValidatesNonEmpty() {
        assertThrows(IllegalArgumentException.class, () -> SegmentConditionGroup.of((SegmentCondition[]) null));
        SegmentConditionGroup group = SegmentConditionGroup.of(
                SegmentCondition.of("email", SegmentOperator.CONTAINS, "@acme.com"));
        assertEquals(1, group.getConditions().size());
    }

    @Test
    void conditionsInputFactoryValidatesNonEmpty() {
        assertThrows(IllegalArgumentException.class, () -> SegmentConditionsInput.of((SegmentConditionGroup[]) null));
        SegmentConditionsInput input = SegmentConditionsInput.of(
                SegmentConditionGroup.of(
                        SegmentCondition.of("email", SegmentOperator.EQUALS, "x")));
        assertEquals(1, input.getGroups().size());
    }

    @Test
    void createOptionsRequiresNameAndConditions() {
        SegmentConditionsInput conds = SegmentConditionsInput.of(
                SegmentConditionGroup.of(
                        SegmentCondition.of("email", SegmentOperator.EQUALS, "x")));

        assertThrows(IllegalArgumentException.class,
                () -> CreateAudienceSegmentOptions.builder().build());
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudienceSegmentOptions.builder().name("Seg").build());
        assertThrows(IllegalArgumentException.class,
                () -> CreateAudienceSegmentOptions.builder().conditions(conds).build());

        CreateAudienceSegmentOptions ok = CreateAudienceSegmentOptions.builder()
                .name("Seg")
                .conditions(conds)
                .listId("l1")
                .build();
        assertEquals("Seg", ok.getName());
        assertEquals("l1", ok.getListId());
        assertNotNull(ok.getConditions());
    }

    @Test
    void updateOptionsAllowsEmpty() {
        UpdateAudienceSegmentOptions empty = UpdateAudienceSegmentOptions.builder().build();
        assertNull(empty.getName());
        assertNull(empty.getConditions());
    }

    @Test
    void serviceArgumentValidation() {
        AudienceSegments svc = new AudienceSegments("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.get(null));
        assertThrows(IllegalArgumentException.class, () -> svc.get(""));
        assertThrows(IllegalArgumentException.class, () -> svc.create(null));
        assertThrows(IllegalArgumentException.class, () -> svc.update(null, null));
        assertThrows(IllegalArgumentException.class,
                () -> svc.update("", UpdateAudienceSegmentOptions.builder().build()));
        assertThrows(IllegalArgumentException.class, () -> svc.update("id", null));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(null));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(""));
    }
}
