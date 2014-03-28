package com.cs446.kluster.net.http;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Created by Marlin Gingerich on 2014-03-08.
 */
public class Request implements HttpRequestWrapper {

    public static enum Method {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE;
    }

    private boolean mCompress = false;
    private boolean mUseCache = false;
    private int connectTimeout;
    private int readTimeout;
    private long ifModifiedSince;
    private boolean followRedirects;
    private boolean ensureSuccess;

    private final Method mMethod;
    private final String mUri;

    private Map<String, Object> params;
    private Map<String, String> headers;

    private HttpUriRequest request;

    public Request(Method method, String uri) {
        this.mMethod = method;
        this.mUri = uri;

        switch(method) {
            case GET:
                this.request = new HttpGet(uri);
                break;
            case POST:
                this.request = new HttpPost(uri);
                break;
            case PUT:
            case PATCH:
                this.request = new HttpPut(uri);
                break;
            case DELETE:
                this.request = new HttpDelete(uri);
                break;
            default:
                this.request = new HttpGet(uri);
                break;
        }
    }

    @Override
    public HttpUriRequest getHttpRequest() {
        return this.request;
    }

    public String getUri() {
        return this.mUri;
    }

    public Method getMethod() {
        return Method.valueOf(this.request.getMethod());
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Request param(String name, Object value) {
        if (this.params == null) {
            this.params = new LinkedHashMap<String, Object>();
        }
        this.params.put(name, value);
        this.request.getParams().setParameter(name, value);
        return this;
    }

    public Request header(String name, String value) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<String, String>();
        }
        this.headers.put(name, value);
        return this;
    }

    public Request compress() {
        mCompress = true;
        return this;
    }

    public Request useCaches(boolean useCache) {
        this.mUseCache = useCache;
        return this;
    }

    public Request ifModifiedSince(long ifModifiedSince) {
        this.ifModifiedSince = ifModifiedSince;
        return this;
    }

    public Request connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public Request readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public Request followRedirects(boolean auto) {
        this.followRedirects = auto;
        return this;
    }

    public Request ensureSuccess() {
        this.ensureSuccess = true;
        return this;
    }
}