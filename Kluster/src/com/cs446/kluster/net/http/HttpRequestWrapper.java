package com.cs446.kluster.net.http;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by Marlin Gingerich on 2014-03-09.
 */
public interface HttpRequestWrapper {
    public HttpUriRequest getHttpRequest();
}