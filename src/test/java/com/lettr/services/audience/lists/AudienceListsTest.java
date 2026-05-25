package com.lettr.services.audience.lists;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.audience.lists.model.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AudienceListsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void audienceListViewDeserializes() {
        String json = "{\"id\":\"00000000-0000-0000-0000-000000000001\"," +
                "\"name\":\"Newsletter\",\"contacts_count\":42}";

        AudienceListView view = gson.fromJson(json, AudienceListView.class);
        assertEquals("00000000-0000-0000-0000-000000000001", view.getId());
        assertEquals("Newsletter", view.getName());
        assertEquals(42, view.getContactsCount());
    }

    @Test
    void listAudienceListsResponseDeserializes() {
        String json = "{\"lists\":[{\"id\":\"abc\",\"name\":\"L1\",\"contacts_count\":1}]," +
                "\"pagination\":{\"total\":1,\"per_page\":20,\"current_page\":1,\"last_page\":1}}";

        ListAudienceListsResponse response = gson.fromJson(json, ListAudienceListsResponse.class);
        assertEquals(1, response.getLists().size());
        assertEquals("L1", response.getLists().get(0).getName());
        assertEquals(1, response.getPagination().getTotal());
        assertEquals(20, response.getPagination().getPerPage());
        assertEquals(1, response.getPagination().getCurrentPage());
        assertEquals(1, response.getPagination().getLastPage());
    }

    @Test
    void bulkDeleteResponseDeserializes() {
        String json = "{\"deleted\":5}";
        BulkDeleteAudienceListsResponse response = gson.fromJson(json, BulkDeleteAudienceListsResponse.class);
        assertEquals(5, response.getDeleted());
    }

    @Test
    void createOptionsRequiresName() {
        assertThrows(IllegalArgumentException.class, () -> CreateAudienceListOptions.of(null));
        assertThrows(IllegalArgumentException.class, () -> CreateAudienceListOptions.of(""));
        assertNotNull(CreateAudienceListOptions.of("ok"));
    }

    @Test
    void bulkDeleteOptionsValidatesBounds() {
        assertThrows(IllegalArgumentException.class, () -> BulkDeleteAudienceListsOptions.of(null));
        assertThrows(IllegalArgumentException.class, () -> BulkDeleteAudienceListsOptions.of(Collections.emptyList()));
        assertNotNull(BulkDeleteAudienceListsOptions.of(Arrays.asList("a", "b")));
    }

    @Test
    void updateOptionsBuilderAllowsEmpty() {
        UpdateAudienceListOptions options = UpdateAudienceListOptions.builder().build();
        assertNull(options.getName());
        UpdateAudienceListOptions named = UpdateAudienceListOptions.builder().name("New").build();
        assertEquals("New", named.getName());
    }

    @Test
    void getRequiresListId() {
        AudienceLists svc = new AudienceLists("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.get(null));
        assertThrows(IllegalArgumentException.class, () -> svc.get(""));
    }

    @Test
    void updateRequiresListIdAndOptions() {
        AudienceLists svc = new AudienceLists("test-key");
        UpdateAudienceListOptions opts = UpdateAudienceListOptions.builder().name("x").build();
        assertThrows(IllegalArgumentException.class, () -> svc.update(null, opts));
        assertThrows(IllegalArgumentException.class, () -> svc.update("", opts));
        assertThrows(IllegalArgumentException.class, () -> svc.update("id", null));
    }

    @Test
    void deleteRequiresListId() {
        AudienceLists svc = new AudienceLists("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.delete(null));
        assertThrows(IllegalArgumentException.class, () -> svc.delete(""));
    }

    @Test
    void createRequiresOptions() {
        AudienceLists svc = new AudienceLists("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.create(null));
    }

    @Test
    void bulkDeleteRequiresOptions() {
        AudienceLists svc = new AudienceLists("test-key");
        assertThrows(IllegalArgumentException.class, () -> svc.bulkDelete(null));
    }
}
