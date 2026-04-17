package com.lettr.services.templates.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Response containing template HTML content and merge tags.
 */
public class GetTemplateHtmlResponse {

    private String html;

    @SerializedName("merge_tags")
    private List<HtmlMergeTag> mergeTags;

    private String subject;

    @Nonnull public String getHtml() { return html; }
    @Nonnull public List<HtmlMergeTag> getMergeTags() { return mergeTags; }
    /** Template subject line, if set. */
    @Nullable public String getSubject() { return subject; }

    /** A merge tag in the HTML template response. */
    public static class HtmlMergeTag {
        private String key;
        private String name;
        private boolean required;

        @Nonnull public String getKey() { return key; }
        @Nonnull public String getName() { return name; }
        public boolean isRequired() { return required; }
    }

    @Override
    public String toString() {
        return "GetTemplateHtmlResponse{subject='" + subject + "'}";
    }
}
