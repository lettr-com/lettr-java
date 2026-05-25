package com.lettr.services.audience.segments.model;

import com.google.gson.annotations.SerializedName;

/**
 * Comparison operator for a segment condition.
 */
public enum SegmentOperator {
    @SerializedName("contains") CONTAINS,
    @SerializedName("not_contains") NOT_CONTAINS,
    @SerializedName("equals") EQUALS,
    @SerializedName("not_equals") NOT_EQUALS,
    @SerializedName("starts_with") STARTS_WITH,
    @SerializedName("not_starts_with") NOT_STARTS_WITH,
    @SerializedName("ends_with") ENDS_WITH,
    @SerializedName("not_ends_with") NOT_ENDS_WITH,
    @SerializedName("is_true") IS_TRUE,
    @SerializedName("is_false") IS_FALSE,
    @SerializedName("greater_than") GREATER_THAN,
    @SerializedName("greater_than_or_equal") GREATER_THAN_OR_EQUAL,
    @SerializedName("less_than") LESS_THAN,
    @SerializedName("less_than_or_equal") LESS_THAN_OR_EQUAL,
    @SerializedName("before") BEFORE,
    @SerializedName("after") AFTER
}
