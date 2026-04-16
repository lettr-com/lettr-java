package com.lettr.services.emails.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Options for scheduling an email for future delivery.
 *
 * <p>Extends {@link CreateEmailOptions} with a required {@code scheduledAt} field.
 * The scheduled time must be at least 5 minutes in the future and within 3 days.</p>
 */
public class ScheduleEmailOptions extends CreateEmailOptions {

    private final String scheduled_at;

    private ScheduleEmailOptions(Builder builder) {
        super(builder);
        this.scheduled_at = builder.scheduledAt;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getScheduledAt() { return scheduled_at; }

    public static class Builder extends CreateEmailOptions.Builder {
        private String scheduledAt;

        protected Builder() {}

        /**
         * <b>(required)</b> Sets the scheduled delivery time in ISO 8601 format.
         * Must be at least 5 minutes in the future and within 3 days.
         */
        @Nonnull
        public Builder scheduledAt(@Nonnull String scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        @Override @Nonnull
        public Builder from(@Nonnull String from) { super.from(from); return this; }
        @Override @Nonnull
        public Builder fromName(@Nullable String fromName) { super.fromName(fromName); return this; }
        @Override @Nonnull
        public Builder to(@Nonnull String... to) { super.to(to); return this; }
        @Override @Nonnull
        public Builder to(@Nonnull List<String> to) { super.to(to); return this; }
        @Override @Nonnull
        public Builder cc(@Nonnull String... cc) { super.cc(cc); return this; }
        @Override @Nonnull
        public Builder cc(@Nullable List<String> cc) { super.cc(cc); return this; }
        @Override @Nonnull
        public Builder bcc(@Nonnull String... bcc) { super.bcc(bcc); return this; }
        @Override @Nonnull
        public Builder bcc(@Nullable List<String> bcc) { super.bcc(bcc); return this; }
        @Override @Nonnull
        public Builder subject(@Nullable String subject) { super.subject(subject); return this; }
        @Override @Nonnull
        public Builder replyTo(@Nullable String replyTo) { super.replyTo(replyTo); return this; }
        @Override @Nonnull
        public Builder replyToName(@Nullable String replyToName) { super.replyToName(replyToName); return this; }
        @Override @Nonnull
        public Builder html(@Nullable String html) { super.html(html); return this; }
        @Override @Nonnull
        public Builder text(@Nullable String text) { super.text(text); return this; }
        @Override @Nonnull
        public Builder ampHtml(@Nullable String ampHtml) { super.ampHtml(ampHtml); return this; }
        @Override @Nonnull
        public Builder templateSlug(@Nullable String templateSlug) { super.templateSlug(templateSlug); return this; }
        @Override @Nonnull
        public Builder templateVersion(int templateVersion) { super.templateVersion(templateVersion); return this; }
        @Override @Nonnull
        public Builder projectId(int projectId) { super.projectId(projectId); return this; }
        @Override @Nonnull
        public Builder tag(@Nullable String tag) { super.tag(tag); return this; }
        @Override @Nonnull
        public Builder headers(@Nullable Map<String, String> headers) { super.headers(headers); return this; }
        @Override @Nonnull
        public Builder attachments(@Nullable List<Attachment> attachments) { super.attachments(attachments); return this; }
        @Override @Nonnull
        public Builder attachments(@Nonnull Attachment... attachments) { super.attachments(attachments); return this; }
        @Override @Nonnull
        public Builder substitutionData(@Nullable Map<String, Object> substitutionData) { super.substitutionData(substitutionData); return this; }
        @Override @Nonnull
        public Builder metadata(@Nullable Map<String, Object> metadata) { super.metadata(metadata); return this; }
        @Override @Nonnull
        public Builder options(@Nullable EmailOptions options) { super.options(options); return this; }

        /**
         * Builds the {@link ScheduleEmailOptions} instance.
         *
         * @throws IllegalArgumentException if {@code from}, {@code to}, content, or {@code scheduledAt} is missing
         */
        @Override @Nonnull
        public ScheduleEmailOptions build() {
            if (from == null || from.isEmpty()) {
                throw new IllegalArgumentException("'from' is required");
            }
            if (to == null || to.isEmpty()) {
                throw new IllegalArgumentException("'to' is required");
            }
            if (html == null && text == null && templateSlug == null) {
                throw new IllegalArgumentException("At least one of 'html', 'text', or 'templateSlug' is required");
            }
            if (scheduledAt == null || scheduledAt.isEmpty()) {
                throw new IllegalArgumentException("'scheduledAt' is required");
            }
            return new ScheduleEmailOptions(this);
        }
    }
}
