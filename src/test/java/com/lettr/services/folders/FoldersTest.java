package com.lettr.services.folders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.folders.model.*;
import com.lettr.services.templates.model.TemplatePurpose;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FoldersTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void listFoldersParamsToQueryParams() {
        ListFoldersParams params = ListFoldersParams.builder()
                .projectId(5)
                .purpose(TemplatePurpose.CAMPAIGN)
                .perPage(50)
                .page(2)
                .build();

        Map<String, String> queryParams = params.toQueryParams();
        assertEquals("5", queryParams.get("project_id"));
        assertEquals("campaign", queryParams.get("purpose"));
        assertEquals("50", queryParams.get("per_page"));
        assertEquals("2", queryParams.get("page"));
    }

    @Test
    void listFoldersParamsEmptyToQueryParams() {
        assertTrue(ListFoldersParams.builder().build().toQueryParams().isEmpty());
    }

    @Test
    void folderDeserializes() {
        String json = "{\"id\":11,\"name\":\"Campaigns\",\"project_id\":5," +
                "\"purpose\":\"campaign\",\"templates_count\":12," +
                "\"created_at\":\"2026-01-15T10:00:00+00:00\"," +
                "\"updated_at\":\"2026-01-20T14:30:00+00:00\"}";

        Folder folder = gson.fromJson(json, Folder.class);

        // The id is the whole point: it is what CreateTemplateOptions.folderId()
        // wants, and nothing else in the SDK returns one.
        assertEquals(11, folder.getId());
        assertEquals("Campaigns", folder.getName());
        assertEquals(5, folder.getProjectId());
        assertEquals(TemplatePurpose.CAMPAIGN, folder.getPurpose());
        assertEquals(12, folder.getTemplatesCount());
    }

    @Test
    void folderWithoutPurposeDefaultsToTransactional() {
        // An API deployment that predates the field.
        String json = "{\"id\":10,\"name\":\"Emails\",\"project_id\":5," +
                "\"created_at\":\"2026-01-15T10:00:00+00:00\"," +
                "\"updated_at\":\"2026-01-20T14:30:00+00:00\"}";

        Folder folder = gson.fromJson(json, Folder.class);

        assertEquals(TemplatePurpose.TRANSACTIONAL, folder.getPurpose());
        assertEquals(0, folder.getTemplatesCount());
    }

    @Test
    void listFoldersResponseDeserializes() {
        String json = "{\"folders\":[{\"id\":10,\"name\":\"Emails\",\"project_id\":5," +
                "\"purpose\":\"transactional\",\"templates_count\":3," +
                "\"created_at\":\"2026-01-15T10:00:00+00:00\"," +
                "\"updated_at\":\"2026-01-20T14:30:00+00:00\"}]," +
                "\"pagination\":{\"total\":1,\"per_page\":25,\"current_page\":1,\"last_page\":1}}";

        ListFoldersResponse response = gson.fromJson(json, ListFoldersResponse.class);

        assertEquals(1, response.getFolders().size());
        assertEquals(10, response.getFolders().get(0).getId());
        assertEquals(1, response.getPagination().getTotal());
    }
}
