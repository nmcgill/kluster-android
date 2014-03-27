package com.cs446.kluster.net.http.task;

import android.os.AsyncTask;

import com.cs446.kluster.net.http.HttpRequestListener;
import com.cs446.kluster.net.http.Request;
import com.cs446.kluster.net.http.Response;
import com.cs446.kluster.data.serialize.Serializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Marlin Gingerich on 2014-03-08.
 */
public class HttpRequestTask<Result> extends AbstractHttpRequestTask<Result> {

    public HttpRequestTask(HttpRequestListener<Result> listener, Serializer<Result> serializer) {
        super(listener, serializer);
    }

    public HttpRequestTask(HttpRequestListener<Result> listener) {
        super(listener, null);
    }

    public HttpRequestTask() {
        super(null, null);
    }

    @Override
    public void executeAsync(Request request) {
        new AsyncTask<Request, Integer, Result>() {

            @Override
            protected void onPreExecute() {
                if (HttpRequestTask.this.mListener != null) {
                    HttpRequestTask.this.mListener.onStart();
                }
            }

            @Override
            protected Result doInBackground(Request... iRequests) {
                if (iRequests.length == 0) {
                    return null;
                }
                Request request = iRequests[0];
                return HttpRequestTask.this.doWork(request);
            }

            @Override
            protected void onPostExecute(Result result) {
                if (HttpRequestTask.this.mListener != null) {
                    HttpRequestTask.this.mListener.onComplete();
                    if (mError != null) {
                        HttpRequestTask.this.mListener.onError(mError);
                        return;
                    }
                    HttpRequestTask.this.mListener.onSuccess(result);
                }
            }
        }.execute(request);
    }

    @Override
    public Result execute(Request request) {
        return doWork(request);
    }

    protected Result parse(Response response) throws IOException {
        if (this.mSerializer != null) {
            InputStreamReader reader = new InputStreamReader(response.getBody());
            return this.mSerializer.read(reader);
        }
        return null;
    }

    private Result doWork(Request request) {
        HttpURLConnection connection = null;
        this.mError = null;

        try {
            connection = createConnection(request);
            connection.connect();

            Response response = Response.create(connection);

            response.ensureSuccess();

            return this.parse(response);
        } catch (Exception e) {
            this.mError = e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }
}