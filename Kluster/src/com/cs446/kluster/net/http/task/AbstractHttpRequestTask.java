package com.cs446.kluster.net.http.task;

import com.cs446.kluster.net.http.HttpRequestListener;
import com.cs446.kluster.net.http.HttpUtils;
import com.cs446.kluster.net.http.Request;
import com.cs446.kluster.data.serialize.Serializer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Marlin Gingerich on 2014-03-10.
 */
abstract class AbstractHttpRequestTask<Result> {

    HttpRequestListener<Result> mListener = null;
    Serializer<Result> mSerializer = null;
    Exception mError = null;

    AbstractHttpRequestTask(HttpRequestListener<Result> listener, Serializer<Result> serializer) {
        this.mListener = listener;
        this.mSerializer = serializer;
    }

    AbstractHttpRequestTask(HttpRequestListener<Result> listener) {
        this(listener, null);
    }

    AbstractHttpRequestTask() {
        this(null, null);
    }

    HttpURLConnection createConnection(Request request) throws IOException {
        String uri = request.getUri();

        if (request.getMethod() == Request.Method.GET &&
                !uri.contains("?") &&
                request.getParams() != null &&
                !request.getParams().isEmpty()) {
            uri += "?" + HttpUtils.queryString(request.getParams());
        }

        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Good place to configure connection
        connection.setRequestMethod(request.getMethod().toString());
        return connection;
    }

    public abstract void executeAsync(Request request);

    public abstract Result execute(Request request);

}