package com.lettr.services;

import com.lettr.core.net.HttpClient;

/**
 * Base class for all Lettr API service classes.
 */
public abstract class BaseService {

    protected final HttpClient httpClient;

    protected BaseService(String apiKey) {
        this.httpClient = new HttpClient(apiKey);
    }
}
