package com.cs446.kluster.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by Marlin Gingerich on 2014-03-09.
 */
public class Response {

    int statusCode;
    String statusLine;
    String responseMessage;
    String contentType;
    long date;
    long lastModified;
    long ifModifiedSince;
    long expiration;
    InputStream body;
    InputStream errorBody;

    Map<String, List<String>> headers;

    private Response() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusLine() {
        return this.statusLine;
    }

    public boolean isSuccess() {
        return (this.statusCode / 100) == 2; // 200, 201, 204, ...
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public String getContentType() {
        return this.contentType;
    }

    public long getDate() {
        return this.date;
    }

    public long getExpiration() {
        return this.expiration;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public String getHeaderField(String field, String defaultValue) {
        try {
            return this.headers.get(field).get(0);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public void ensureSuccess() throws Exception {
        if (!isSuccess()) {
            throw new Exception("Request failed: " + statusCode + " " + responseMessage);
        }
    }

    public InputStream getBody() {
        return body;
    }

    public InputStream getErrorBody() {
        return errorBody;
    }

    public static Response create(HttpURLConnection connection) {
        if (connection == null) {
            return null;
        }
        Response res = new Response();

        try {
            res.statusCode = connection.getResponseCode();
            res.statusLine = connection.getHeaderField(null);
            res.responseMessage = connection.getResponseMessage();
            res.contentType = connection.getContentType();
            res.date = connection.getDate();
            res.lastModified = connection.getLastModified();
            res.ifModifiedSince = connection.getIfModifiedSince();
            res.expiration = connection.getExpiration();
            res.headers = connection.getHeaderFields();
            res.body = connection.getInputStream();
        } catch (IOException e) {
            res.errorBody = connection.getErrorStream();
        }

        return res;
    }
}