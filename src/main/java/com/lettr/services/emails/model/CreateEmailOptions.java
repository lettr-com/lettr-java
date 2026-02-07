package com.lettr.services.emails.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Options for sending an email via the Lettr API.
 *
 * <p>At minimum, {@code from}, {@code to}, {@code subject}, and either {@code html} or {@code text} are required.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CreateEmailOptions params = CreateEmailOptions.builder()
 *     .from("sender@example.com")
 *     .to("recipient@example.com")
 *     .subject("Hello!")
 *     .html("<p>Hello, world!</p>")
 *     .build();
 * }</pre>
 */
public class CreateEmailOptions {

    private final String from;
    private final String from_name;
    private final List<String> to;
    private final String subject;
    private final String html;
    private final String text;
    private final String template_slug;
    private final Integer template_version;
    private final Integer project_id;
    private final List<Attachment> attachments;
    private final Map<String, Object> substitution_data;
    private final Map<String, Object> metadata;
    private final EmailOptions options;

    private CreateEmailOptions(Builder builder) {
        this.from = builder.from;
        this.from_name = builder.fromName;
        this.to = builder.to;
        this.subject = builder.subject;
        this.html = builder.html;
        this.text = builder.text;
        this.template_slug = builder.templateSlug;
        this.template_version = builder.templateVersion;
        this.project_id = builder.projectId;
        this.attachments = builder.attachments;
        this.substitution_data = builder.substitutionData;
        this.metadata = builder.metadata;
        this.options = builder.options;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFrom() { return from; }
    public String getFromName() { return from_name; }
    public List<String> getTo() { return to; }
    public String getSubject() { return subject; }
    public String getHtml() { return html; }
    public String getText() { return text; }
    public String getTemplateSlug() { return template_slug; }
    public Integer getTemplateVersion() { return template_version; }
    public Integer getProjectId() { return project_id; }
    public List<Attachment> getAttachments() { return attachments; }
    public Map<String, Object> getSubstitutionData() { return substitution_data; }
    public Map<String, Object> getMetadata() { return metadata; }
    public EmailOptions getOptions() { return options; }

    public static class Builder {
        private String from;
        private String fromName;
        private List<String> to;
        private String subject;
        private String html;
        private String text;
        private String templateSlug;
        private Integer templateVersion;
        private Integer projectId;
        private List<Attachment> attachments;
        private Map<String, Object> substitutionData;
        private Map<String, Object> metadata;
        private EmailOptions options;

        private Builder() {}

        /**
         * Sets the sender email address (required).
         */
        public Builder from(String from) {
            this.from = from;
            return this;
        }

        /**
         * Sets the sender display name.
         */
        public Builder fromName(String fromName) {
            this.fromName = fromName;
            return this;
        }

        /**
         * Sets recipient email addresses (required). Accepts varargs.
         */
        public Builder to(String... to) {
            this.to = Arrays.asList(to);
            return this;
        }

        /**
         * Sets recipient email addresses (required). Accepts a list.
         */
        public Builder to(List<String> to) {
            this.to = to;
            return this;
        }

        /**
         * Sets the email subject line (required).
         */
        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * Sets the HTML content of the email. At least one of html or text is required.
         */
        public Builder html(String html) {
            this.html = html;
            return this;
        }

        /**
         * Sets the plain text content of the email. At least one of html or text is required.
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the template slug to use for this email.
         */
        public Builder templateSlug(String templateSlug) {
            this.templateSlug = templateSlug;
            return this;
        }

        /**
         * Sets a specific template version to use.
         */
        public Builder templateVersion(int templateVersion) {
            this.templateVersion = templateVersion;
            return this;
        }

        /**
         * Sets the project ID for template lookup.
         */
        public Builder projectId(int projectId) {
            this.projectId = projectId;
            return this;
        }

        /**
         * Sets file attachments for the email.
         */
        public Builder attachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        /**
         * Sets file attachments for the email. Accepts varargs.
         */
        public Builder attachments(Attachment... attachments) {
            this.attachments = Arrays.asList(attachments);
            return this;
        }

        /**
         * Sets substitution data for template variable replacement.
         * Variables in your template like {@code {{first_name}}} will be replaced.
         */
        public Builder substitutionData(Map<String, Object> substitutionData) {
            this.substitutionData = substitutionData;
            return this;
        }

        /**
         * Sets metadata to attach to the email for tracking purposes.
         */
        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * Sets tracking options (click tracking, open tracking, etc.).
         */
        public Builder options(EmailOptions options) {
            this.options = options;
            return this;
        }

        /**
         * Builds the CreateEmailOptions instance.
         *
         * @throws IllegalArgumentException if required fields are missing
         */
        public CreateEmailOptions build() {
            if (from == null || from.isEmpty()) {
                throw new IllegalArgumentException("'from' is required");
            }
            if (to == null || to.isEmpty()) {
                throw new IllegalArgumentException("'to' is required");
            }
            if (subject == null || subject.isEmpty()) {
                throw new IllegalArgumentException("'subject' is required");
            }
            if (html == null && text == null && templateSlug == null) {
                throw new IllegalArgumentException("At least one of 'html', 'text', or 'templateSlug' is required");
            }
            return new CreateEmailOptions(this);
        }
    }
}
