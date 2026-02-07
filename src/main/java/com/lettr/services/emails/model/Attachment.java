package com.lettr.services.emails.model;

/**
 * Represents a file attachment for an email.
 * The file data must be base64 encoded.
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

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public static class Builder {
        private String name;
        private String type;
        private String data;

        private Builder() {}

        /**
         * Sets the filename of the attachment (e.g. "invoice.pdf").
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the MIME type of the attachment (e.g. "application/pdf").
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the base64-encoded file content.
         */
        public Builder data(String data) {
            this.data = data;
            return this;
        }

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
