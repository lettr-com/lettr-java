package com.lettr.services.templates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.templates.model.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemplatesTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    // --- CreateTemplateOptions tests ---

    @Test
    void createTemplateOptionsRequiresName() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateTemplateOptions.builder().html("<p>Hello</p>").build());
    }

    @Test
    void createTemplateOptionsRequiresContent() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateTemplateOptions.builder().name("Test").build());
    }

    @Test
    void createTemplateOptionsMutualExclusivity() {
        assertThrows(IllegalArgumentException.class, () ->
                CreateTemplateOptions.builder()
                        .name("Test")
                        .html("<p>Hello</p>")
                        .json("{\"tag\":\"mj-body\"}")
                        .build());
    }

    @Test
    void createTemplateOptionsBuildsWithHtml() {
        CreateTemplateOptions options = CreateTemplateOptions.builder()
                .name("Welcome")
                .html("<p>Hello {{NAME}}</p>")
                .projectId(5)
                .folderId(10)
                .build();

        assertEquals("Welcome", options.getName());
        assertEquals("<p>Hello {{NAME}}</p>", options.getHtml());
        assertNull(options.getJson());
        assertEquals(5, options.getProjectId());
        assertEquals(10, options.getFolderId());
    }

    @Test
    void createTemplateOptionsBuildsWithJson() {
        CreateTemplateOptions options = CreateTemplateOptions.builder()
                .name("Welcome")
                .json("{\"tag\":\"mj-body\"}")
                .build();

        assertNull(options.getHtml());
        assertEquals("{\"tag\":\"mj-body\"}", options.getJson());
    }

    // --- UpdateTemplateOptions tests ---

    @Test
    void updateTemplateOptionsMutualExclusivity() {
        assertThrows(IllegalArgumentException.class, () ->
                UpdateTemplateOptions.builder()
                        .html("<p>Hello</p>")
                        .json("{\"tag\":\"mj-body\"}")
                        .build());
    }

    @Test
    void updateTemplateOptionsBuildsWithNoFields() {
        UpdateTemplateOptions options = UpdateTemplateOptions.builder().build();
        assertNotNull(options);
    }

    @Test
    void updateTemplateOptionsBuildsWithAllFields() {
        UpdateTemplateOptions options = UpdateTemplateOptions.builder()
                .projectId(5)
                .name("Updated")
                .html("<p>Updated content</p>")
                .build();

        assertEquals(5, options.getProjectId());
        assertEquals("Updated", options.getName());
        assertEquals("<p>Updated content</p>", options.getHtml());
    }

    // --- ListTemplatesParams tests ---

    @Test
    void listTemplatesParamsToQueryParams() {
        ListTemplatesParams params = ListTemplatesParams.builder()
                .projectId(5)
                .perPage(25)
                .page(2)
                .build();

        Map<String, String> queryParams = params.toQueryParams();
        assertEquals("5", queryParams.get("project_id"));
        assertEquals("25", queryParams.get("per_page"));
        assertEquals("2", queryParams.get("page"));
    }

    // --- GetMergeTagsParams tests ---

    @Test
    void getMergeTagsParamsToQueryParams() {
        GetMergeTagsParams params = GetMergeTagsParams.builder()
                .projectId(5)
                .version(2)
                .build();

        Map<String, String> queryParams = params.toQueryParams();
        assertEquals("5", queryParams.get("project_id"));
        assertEquals("2", queryParams.get("version"));
    }

    // --- GetTemplateHtmlParams tests ---

    @Test
    void getTemplateHtmlParamsRequiresSlug() {
        assertThrows(IllegalArgumentException.class, () ->
                GetTemplateHtmlParams.builder().projectId(5).build());
    }

    @Test
    void getTemplateHtmlParamsToQueryParams() {
        GetTemplateHtmlParams params = GetTemplateHtmlParams.builder()
                .projectId(5)
                .slug("welcome-email")
                .build();

        Map<String, String> queryParams = params.toQueryParams();
        assertEquals("5", queryParams.get("project_id"));
        assertEquals("welcome-email", queryParams.get("slug"));
    }

    // --- Template deserialization ---

    @Test
    void templateDeserializes() {
        String json = "{\"id\":1,\"name\":\"Welcome\",\"slug\":\"welcome\"," +
                "\"project_id\":5,\"folder_id\":10," +
                "\"created_at\":\"2025-01-15T10:00:00+00:00\",\"updated_at\":\"2025-01-20T14:30:00+00:00\"}";

        Template template = gson.fromJson(json, Template.class);
        assertEquals(1, template.getId());
        assertEquals("Welcome", template.getName());
        assertEquals("welcome", template.getSlug());
        assertEquals(5, template.getProjectId());
        assertEquals(10, template.getFolderId());
    }

    @Test
    void templateDetailDeserializes() {
        String json = "{\"id\":1,\"name\":\"Welcome\",\"slug\":\"welcome\"," +
                "\"project_id\":5,\"folder_id\":10,\"active_version\":2,\"versions_count\":3," +
                "\"html\":\"<p>Hello</p>\",\"json\":null," +
                "\"created_at\":\"2025-01-15T10:00:00+00:00\",\"updated_at\":\"2025-01-20T14:30:00+00:00\"}";

        TemplateDetail detail = gson.fromJson(json, TemplateDetail.class);
        assertEquals(1, detail.getId());
        assertEquals("welcome", detail.getSlug());
        assertEquals(2, detail.getActiveVersion());
        assertEquals(3, detail.getVersionsCount());
        assertEquals("<p>Hello</p>", detail.getHtml());
        assertNull(detail.getJson());
    }

    @Test
    void createTemplateResponseDeserializes() {
        String json = "{\"id\":123,\"name\":\"Welcome\",\"slug\":\"welcome\"," +
                "\"project_id\":5,\"folder_id\":10,\"active_version\":1," +
                "\"merge_tags\":[{\"key\":\"first_name\",\"required\":true}]," +
                "\"created_at\":\"2026-01-28T12:00:00+00:00\"}";

        CreateTemplateResponse response = gson.fromJson(json, CreateTemplateResponse.class);
        assertEquals(123, response.getId());
        assertEquals("welcome", response.getSlug());
        assertEquals(1, response.getActiveVersion());
        assertEquals(1, response.getMergeTags().size());
        assertEquals("first_name", response.getMergeTags().get(0).getKey());
        assertTrue(response.getMergeTags().get(0).isRequired());
    }

    @Test
    void mergeTagWithChildrenDeserializes() {
        String json = "{\"key\":\"items\",\"required\":true,\"type\":\"text\"," +
                "\"children\":[{\"key\":\"item_name\",\"type\":\"text\"},{\"key\":\"price\",\"type\":\"number\"}]}";

        MergeTag tag = gson.fromJson(json, MergeTag.class);
        assertEquals("items", tag.getKey());
        assertTrue(tag.isRequired());
        assertEquals("text", tag.getType());
        assertEquals(2, tag.getChildren().size());
        assertEquals("item_name", tag.getChildren().get(0).getKey());
        assertEquals("number", tag.getChildren().get(1).getType());
    }

    @Test
    void listTemplatesResponseDeserializes() {
        String json = "{\"templates\":[{\"id\":1,\"name\":\"Welcome\",\"slug\":\"welcome\"," +
                "\"project_id\":5,\"folder_id\":10," +
                "\"created_at\":\"2025-01-15T10:00:00+00:00\",\"updated_at\":\"2025-01-20T14:30:00+00:00\"}]," +
                "\"pagination\":{\"total\":1,\"per_page\":25,\"current_page\":1,\"last_page\":1}}";

        ListTemplatesResponse response = gson.fromJson(json, ListTemplatesResponse.class);
        assertEquals(1, response.getTemplates().size());
        assertEquals("welcome", response.getTemplates().get(0).getSlug());
        assertEquals(1, response.getPagination().getTotal());
        assertEquals(25, response.getPagination().getPerPage());
    }

    @Test
    void getMergeTagsResponseDeserializes() {
        String json = "{\"project_id\":13,\"template_slug\":\"welcome\",\"version\":2," +
                "\"merge_tags\":[{\"key\":\"name\",\"required\":true}]}";

        GetMergeTagsResponse response = gson.fromJson(json, GetMergeTagsResponse.class);
        assertEquals(13, response.getProjectId());
        assertEquals("welcome", response.getTemplateSlug());
        assertEquals(2, response.getVersion());
        assertEquals(1, response.getMergeTags().size());
    }

    @Test
    void getTemplateHtmlResponseDeserializes() {
        String json = "{\"html\":\"<p>Hello</p>\",\"merge_tags\":[{\"key\":\"name\",\"name\":\"Name\",\"required\":true}]," +
                "\"subject\":\"Welcome\"}";

        GetTemplateHtmlResponse response = gson.fromJson(json, GetTemplateHtmlResponse.class);
        assertEquals("<p>Hello</p>", response.getHtml());
        assertEquals("Welcome", response.getSubject());
        assertEquals(1, response.getMergeTags().size());
        assertEquals("name", response.getMergeTags().get(0).getKey());
        assertEquals("Name", response.getMergeTags().get(0).getName());
    }

    // --- Service argument validation ---

    @Test
    void templatesGetRequiresSlug() {
        Templates templates = new Templates("test-key");
        assertThrows(IllegalArgumentException.class, () -> templates.get(null));
        assertThrows(IllegalArgumentException.class, () -> templates.get(""));
    }

    @Test
    void templatesUpdateRequiresSlug() {
        Templates templates = new Templates("test-key");
        UpdateTemplateOptions options = UpdateTemplateOptions.builder().name("x").build();
        assertThrows(IllegalArgumentException.class, () -> templates.update(null, options));
        assertThrows(IllegalArgumentException.class, () -> templates.update("", options));
    }

    @Test
    void templatesDeleteRequiresSlug() {
        Templates templates = new Templates("test-key");
        assertThrows(IllegalArgumentException.class, () -> templates.delete(null));
        assertThrows(IllegalArgumentException.class, () -> templates.delete(""));
    }

    @Test
    void templatesGetMergeTagsRequiresSlug() {
        Templates templates = new Templates("test-key");
        assertThrows(IllegalArgumentException.class, () -> templates.getMergeTags(null));
        assertThrows(IllegalArgumentException.class, () -> templates.getMergeTags(""));
    }
}
