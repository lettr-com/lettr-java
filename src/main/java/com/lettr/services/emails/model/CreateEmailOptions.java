package com.lettr.services.emails.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Options for sending an email via the Lettr API.
 *
 * <p>At minimum, {@code from}, {@code to}, and at least one of {@code html}, {@code text},
 * or {@code templateSlug} are required. The {@code subject} is required unless using a template.</p>
 *
 * <p>Example:</p>
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
    private final List<String> cc;
    private final List<String> bcc;
    private final String subject;
    private final String reply_to;
    private final String reply_to_name;
    private final String html;
    private final String text;
    private final String amp_html;
    private final String template_slug;
    private final Integer template_version;
    private final Integer project_id;
    private final String tag;
    private final Map<String, String> headers;
    private final List<Attachment> attachments;
    private final Map<String, String> substitution_data;
    private final Map<String, String> metadata;
    private final EmailOptions options;

    protected CreateEmailOptions(Builder builder) {
        this.from = builder.from;
        this.from_name = builder.fromName;
        this.to = builder.to;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
        this.subject = builder.subject;
        this.reply_to = builder.replyTo;
        this.reply_to_name = builder.replyToName;
        this.html = builder.html;
        this.text = builder.text;
        this.amp_html = builder.ampHtml;
        this.template_slug = builder.templateSlug;
        this.template_version = builder.templateVersion;
        this.project_id = builder.projectId;
        this.tag = builder.tag;
        this.headers = builder.headers;
        this.attachments = builder.attachments;
        this.substitution_data = builder.substitutionData;
        this.metadata = builder.metadata;
        this.options = builder.options;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getFrom() { return from; }
    @Nullable public String getFromName() { return from_name; }
    @Nonnull public List<String> getTo() { return to; }
    @Nullable public List<String> getCc() { return cc; }
    @Nullable public List<String> getBcc() { return bcc; }
    @Nullable public String getSubject() { return subject; }
    @Nullable public String getReplyTo() { return reply_to; }
    @Nullable public String getReplyToName() { return reply_to_name; }
    @Nullable public String getHtml() { return html; }
    @Nullable public String getText() { return text; }
    @Nullable public String getAmpHtml() { return amp_html; }
    @Nullable public String getTemplateSlug() { return template_slug; }
    @Nullable public Integer getTemplateVersion() { return template_version; }
    @Nullable public Integer getProjectId() { return project_id; }
    @Nullable public String getTag() { return tag; }
    @Nullable public Map<String, String> getHeaders() { return headers; }
    @Nullable public List<Attachment> getAttachments() { return attachments; }
    @Nullable public Map<String, String> getSubstitutionData() { return substitution_data; }
    @Nullable public Map<String, String> getMetadata() { return metadata; }
    @Nullable public EmailOptions getOptions() { return options; }

    public static class Builder {
        protected String from;
        protected String fromName;
        protected List<String> to;
        protected List<String> cc;
        protected List<String> bcc;
        protected String subject;
        protected String replyTo;
        protected String replyToName;
        protected String html;
        protected String text;
        protected String ampHtml;
        protected String templateSlug;
        protected Integer templateVersion;
        protected Integer projectId;
        protected String tag;
        protected Map<String, String> headers;
        protected List<Attachment> attachments;
        protected Map<String, String> substitutionData;
        protected Map<String, String> metadata;
        protected EmailOptions options;

        protected Builder() {}

        /**
         * <b>(required)</b> Sets the sender email address.
         * Max length: 255 characters. Must be a valid email address.
         */
        @Nonnull
        public Builder from(@Nonnull String from) {
            this.from = from;
            return this;
        }

        /**
         * <b>(optional)</b> Sets the sender display name.
         * Max length: 255 characters.
         */
        @Nonnull
        public Builder fromName(@Nullable String fromName) {
            this.fromName = fromName;
            return this;
        }

        /**
         * <b>(required)</b> Sets recipient email addresses (1–50 items). Accepts varargs.
         */
        @Nonnull
        public Builder to(@Nonnull String... to) {
            this.to = Arrays.asList(to);
            return this;
        }

        /**
         * <b>(required)</b> Sets recipient email addresses (1–50 items). Accepts a list.
         */
        @Nonnull
        public Builder to(@Nonnull List<String> to) {
            this.to = to;
            return this;
        }

        /**
         * <b>(optional)</b> Sets carbon-copy recipient email addresses.
         */
        @Nonnull
        public Builder cc(@Nonnull String... cc) {
            this.cc = Arrays.asList(cc);
            return this;
        }

        /**
         * <b>(optional)</b> Sets carbon-copy recipient email addresses.
         */
        @Nonnull
        public Builder cc(@Nullable List<String> cc) {
            this.cc = cc;
            return this;
        }

        /**
         * <b>(optional)</b> Sets blind-carbon-copy recipient email addresses.
         */
        @Nonnull
        public Builder bcc(@Nonnull String... bcc) {
            this.bcc = Arrays.asList(bcc);
            return this;
        }

        /**
         * <b>(optional)</b> Sets blind-carbon-copy recipient email addresses.
         */
        @Nonnull
        public Builder bcc(@Nullable List<String> bcc) {
            this.bcc = bcc;
            return this;
        }

        /**
         * <b>(required unless using template)</b> Sets the email subject line.
         * Max length: 998 characters. When using {@link #templateSlug(String)}, this is
         * optional and defaults to the template's subject.
         */
        @Nonnull
        public Builder subject(@Nullable String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * <b>(optional)</b> Sets the reply-to email address.
         * Max length: 255 characters.
         */
        @Nonnull
        public Builder replyTo(@Nullable String replyTo) {
            this.replyTo = replyTo;
            return this;
        }

        /**
         * <b>(optional)</b> Sets the reply-to display name.
         */
        @Nonnull
        public Builder replyToName(@Nullable String replyToName) {
            this.replyToName = replyToName;
            return this;
        }

        /**
         * <b>(required unless text or template provided)</b> Sets the HTML content of the email.
         * At least one of {@code html}, {@code text}, or {@link #templateSlug(String)} is required.
         */
        @Nonnull
        public Builder html(@Nullable String html) {
            this.html = html;
            return this;
        }

        /**
         * <b>(required unless html or template provided)</b> Sets the plain-text content of the email.
         */
        @Nonnull
        public Builder text(@Nullable String text) {
            this.text = text;
            return this;
        }

        /**
         * <b>(optional)</b> Sets AMP HTML content for AMP-supporting email clients.
         */
        @Nonnull
        public Builder ampHtml(@Nullable String ampHtml) {
            this.ampHtml = ampHtml;
            return this;
        }

        /**
         * <b>(required unless html or text provided)</b> Sets the template slug to use for this email.
         * Max length: 255 characters.
         */
        @Nonnull
        public Builder templateSlug(@Nullable String templateSlug) {
            this.templateSlug = templateSlug;
            return this;
        }

        /**
         * <b>(optional)</b> Sets a specific template version (minimum 1).
         * Defaults to the active version when not set.
         */
        @Nonnull
        public Builder templateVersion(int templateVersion) {
            this.templateVersion = templateVersion;
            return this;
        }

        /**
         * <b>(optional)</b> Sets the project ID for template lookup.
         * Defaults to the team's default project when not set.
         */
        @Nonnull
        public Builder projectId(int projectId) {
            this.projectId = projectId;
            return this;
        }

        /**
         * <b>(optional)</b> Sets a tag for tracking and analytics.
         * Max length: 64 characters. Auto-set from {@link #templateSlug(String)} if not provided.
         */
        @Nonnull
        public Builder tag(@Nullable String tag) {
            this.tag = tag;
            return this;
        }

        /**
         * <b>(optional)</b> Sets custom email headers. Up to 10 headers.
         * Standard envelope headers (From, To, Subject, etc.) cannot be set here.
         */
        @Nonnull
        public Builder headers(@Nullable Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * <b>(optional)</b> Sets file attachments.
         */
        @Nonnull
        public Builder attachments(@Nullable List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        /**
         * <b>(optional)</b> Sets file attachments. Accepts varargs.
         */
        @Nonnull
        public Builder attachments(@Nonnull Attachment... attachments) {
            this.attachments = Arrays.asList(attachments);
            return this;
        }

        /**
         * <b>(optional)</b> Sets substitution data for template variable replacement.
         */
        @Nonnull
        public Builder substitutionData(@Nullable Map<String, String> substitutionData) {
            this.substitutionData = substitutionData;
            return this;
        }

        /**
         * <b>(optional)</b> Sets metadata to attach to the email for tracking purposes.
         */
        @Nonnull
        public Builder metadata(@Nullable Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * <b>(optional)</b> Sets tracking and delivery options.
         */
        @Nonnull
        public Builder options(@Nullable EmailOptions options) {
            this.options = options;
            return this;
        }

        /**
         * Builds the {@link CreateEmailOptions} instance.
         *
         * @throws IllegalArgumentException if {@code from} or {@code to} is missing,
         *         or if none of {@code html}, {@code text}, or {@code templateSlug} is provided
         */
        @Nonnull
        public CreateEmailOptions build() {
            if (from == null || from.isEmpty()) {
                throw new IllegalArgumentException("'from' is required");
            }
            if (to == null || to.isEmpty()) {
                throw new IllegalArgumentException("'to' is required");
            }
            if (html == null && text == null && templateSlug == null) {
                throw new IllegalArgumentException("At least one of 'html', 'text', or 'templateSlug' is required");
            }
            return new CreateEmailOptions(this);
        }
    }
}
