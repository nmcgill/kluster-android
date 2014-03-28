package com.cs446.kluster.net.http.task;

import android.os.AsyncTask;

import com.cs446.kluster.net.http.HttpRequestListener;
import com.cs446.kluster.net.http.Request;
import com.cs446.kluster.data.serialize.Serializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Marlin Gingerich on 2014-03-08.
 */
public class HttpRequestTaskCompat<Result> extends AbstractHttpRequestTask<Result> {

    public HttpRequestTaskCompat(HttpRequestListener<Result> listener, Serializer<Result> serializer) {
        super(listener, serializer);
    }

    public HttpRequestTaskCompat(HttpRequestListener<Result> listener) {
        super(listener, null);
    }

    public HttpRequestTaskCompat() {
        super(null, null);
    }

    @Override
    public void executeAsync(Request request) {
        new AsyncTask<Request, Integer, Result>() {

            @Override
            protected void onPreExecute() {
                if (HttpRequestTaskCompat.this.mListener != null) {
                    HttpRequestTaskCompat.this.mListener.onStart();
                }
            }

            @Override
            protected Result doInBackground(Request... iRequests) {
                if (iRequests.length == 0) {
                    return null;
                }
                Request request = iRequests[0];
                return HttpRequestTaskCompat.this.doWork(request);
            }

            @Override
            protected void onPostExecute(Result result) {
                if (HttpRequestTaskCompat.this.mListener != null) {
                    HttpRequestTaskCompat.this.mListener.onComplete();
                    if (mError != null) {
                        HttpRequestTaskCompat.this.mListener.onError(mError);
                        return;
                    }
                    HttpRequestTaskCompat.this.mListener.onSuccess(result);
                }
            }
        }.execute(request);
    }

    @Override
    public Result execute(Request request) {
        return doWork(request);
    }

    protected Result parse(HttpEntity content) throws IOException {
        if (this.mSerializer != null) {
            return this.mSerializer.read(new InputStreamReader(content.getContent()));
        }
        return null;
    }

    private Result doWork(Request request) {
        HttpUriRequest req = request.getHttpRequest();
        HttpClient httpclient = new DefaultHttpClient();
        this.mError = null;

        try {
            HttpResponse response = httpclient.execute(req);
            if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
                this.mError = new Exception(response.getStatusLine().toString());
                return null;
            }
            return this.parse(response.getEntity());
        } catch (Exception e) {
            this.mError = e;
        }

        return null;
    }
}