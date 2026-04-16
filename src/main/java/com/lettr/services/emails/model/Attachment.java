package com.lettr.services.emails.model;

import javax.annotation.Nonnull;

/**
 * Represents a file attachment for an email.
 * All three fields ({@code name}, {@code type}, {@code data}) are required.
 */
public class Attachment {

    private final String name;
    private final String type;
    private final String data;

    private Attachment(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.data = builder.data;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull public String getName() { return name; }
    @Nonnull public String getType() { return type; }
    @Nonnull public String getData() { return data; }

    public static class Builder {
        private String name;
        private String type;
        private String data;

        private Builder() {}

        /**
         * <b>(required)</b> Sets the filename of the attachment (e.g. "invoice.pdf").
         * Max length: 255 characters.
         */
        @Nonnull
        public Builder name(@Nonnull String name) {
            this.name = name;
            return this;
        }

        /**
         * <b>(required)</b> Sets the MIME type of the attachment (e.g. "application/pdf").
         * Max length: 255 characters.
         */
        @Nonnull
        public Builder type(@Nonnull String type) {
            this.type = type;
            return this;
        }

        /**
         * <b>(required)</b> Sets the base64-encoded file content (no line breaks).
         */
        @Nonnull
        public Builder data(@Nonnull String data) {
            this.data = data;
            return this;
        }

        /**
         * Builds the {@link Attachment} instance.
         *
         * @throws IllegalArgumentException if {@code name}, {@code type}, or {@code data} is missing
         */
        @Nonnull
        public Attachment build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Attachment name is required");
            }
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("Attachment type is required");
            }
            if (data == null || data.isEmpty()) {
                throw new IllegalArgumentException("Attachment data is required");
            }
            return new Attachment(this);
        }
    }
}
