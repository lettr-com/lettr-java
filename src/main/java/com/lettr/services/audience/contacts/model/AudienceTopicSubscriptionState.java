package com.lettr.services.audience.contacts.model;

import com.google.gson.annotations.SerializedName;

/**
 * What a write request should <em>do</em> with a topic.
 *
 * <p>Deliberately separate from
 * {@link com.lettr.services.audience.topics.model.AudienceTopicDefaultSubscription},
 * which describes how a topic behaves for a contact that says nothing.
 * {@link #OPT_OUT} here also cancels the auto-subscription a topic whose
 * default is opt-out would otherwise give a newly created contact, so a create
 * and an unsubscribe fit in one request.
 */
public enum AudienceTopicSubscriptionState {
    @SerializedName("opt_in") OPT_IN,
    @SerializedName("opt_out") OPT_OUT
}
