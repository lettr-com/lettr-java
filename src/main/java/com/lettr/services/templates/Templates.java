package com.lettr.services.templates;

import com.lettr.core.exception.LettrException;
import com.lettr.services.BaseService;
import com.lettr.services.templates.model.CreateTemplateOptions;
import com.lettr.services.templates.model.CreateTemplateResponse;
import com.lettr.services.templates.model.ListTemplatesParams;
import com.lettr.services.templates.model.ListTemplatesResponse;

/**
 * Service for managing email templates via the Lettr API.
 *
 * <p>Example:</p>
 * <pre>{@code
 * Lettr lettr = new Lettr("your-api-key");
 *
 * // List all templates
 * ListTemplatesResponse templates = lettr.templates().list();
 *
 * // Create a new template
 * CreateTemplateResponse response = lettr.templates().create(
 *     CreateTemplateOptions.builder()
 *         .name("Welcome Email")
 *         .html("<p>Hello {{FIRST_NAME}}!</p>")
 *         .build()
 * );
 * }</pre>
 */
public class Templates extends BaseService {

    public Templates(String apiKey) {
        super(apiKey);
    }

    /**
     * List templates with optional filtering and pagination.
     *
     * @param params optional query parameters
     * @return paginated list of templates
     * @throws LettrException if the request fails
     */
    public ListTemplatesResponse list(ListTemplatesParams params) throws LettrException {
        return httpClient.get("/templates", params != null ? params.toQueryParams() : null, ListTemplatesResponse.class);
    }

    /**
     * List templates with default pagination.
     *
     * @return paginated list of templates
     * @throws LettrException if the request fails
     */
    public ListTemplatesResponse list() throws LettrException {
        return list(null);
    }

    /**
     * Create a new email template.
     *
     * @param options template creation options
     * @return response containing the created template info
     * @throws LettrException if the request fails
     */
    public CreateTemplateResponse create(CreateTemplateOptions options) throws LettrException {
        return httpClient.post("/templates", options, CreateTemplateResponse.class);
    }
}
