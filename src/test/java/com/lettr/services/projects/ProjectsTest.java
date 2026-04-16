package com.lettr.services.projects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.projects.model.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProjectsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void listProjectsParamsToQueryParams() {
        ListProjectsParams params = ListProjectsParams.builder()
                .perPage(10)
                .page(2)
                .build();

        Map<String, String> queryParams = params.toQueryParams();
        assertEquals("10", queryParams.get("per_page"));
        assertEquals("2", queryParams.get("page"));
    }

    @Test
    void listProjectsParamsEmptyToQueryParams() {
        ListProjectsParams params = ListProjectsParams.builder().build();
        assertTrue(params.toQueryParams().isEmpty());
    }

    @Test
    void projectDeserializes() {
        String json = "{\"id\":1,\"name\":\"My Project\",\"emoji\":\"\\uD83D\\uDE80\"," +
                "\"team_id\":42,\"created_at\":\"2025-01-15T10:00:00+00:00\"," +
                "\"updated_at\":\"2025-01-20T14:30:00+00:00\"}";

        Project project = gson.fromJson(json, Project.class);
        assertEquals(1, project.getId());
        assertEquals("My Project", project.getName());
        assertEquals(42, project.getTeamId());
        assertNotNull(project.getCreatedAt());
    }

    @Test
    void listProjectsResponseDeserializes() {
        String json = "{\"projects\":[{\"id\":1,\"name\":\"Default\",\"team_id\":1," +
                "\"created_at\":\"2025-01-01T00:00:00+00:00\",\"updated_at\":\"2025-01-01T00:00:00+00:00\"}]," +
                "\"pagination\":{\"total\":1,\"per_page\":25,\"current_page\":1,\"last_page\":1}}";

        ListProjectsResponse response = gson.fromJson(json, ListProjectsResponse.class);
        assertEquals(1, response.getProjects().size());
        assertEquals("Default", response.getProjects().get(0).getName());
        assertEquals(1, response.getPagination().getTotal());
        assertEquals(25, response.getPagination().getPerPage());
        assertEquals(1, response.getPagination().getCurrentPage());
        assertEquals(1, response.getPagination().getLastPage());
    }
}
